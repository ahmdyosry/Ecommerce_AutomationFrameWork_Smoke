package listeners;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LogEntry;
import org.testng.*;
import reporting.ExtentReportManager;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ScreenshotUtils;


public class TestListener extends BaseTest implements ITestListener, IConfigurationListener, ISuiteListener {      // we implement ITestListener Interface
    private final ExtentReports extent = ExtentReportManager.getReportObject();
    private final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static final ConcurrentHashMap<String, AtomicInteger> attempts = new ConcurrentHashMap<>();
    private static final String ATTEMPT_KEY = "attemptKey";

    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        String browserName = getBrowserNameSafely(result);
        String parameters = Arrays.deepToString(result.getParameters());

        String key = className + "." + methodName + "." + browserName + "." + parameters;

        result.setAttribute(ATTEMPT_KEY, key);

        int attempt = attempts.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet();

        String testName = String.format("%s | %s | [%s] | Attempt %d", className, methodName, browserName, attempt);


        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);

    }


    @Override
    public void onConfigurationFailure(ITestResult result) {

        ExtentTest currentExtentTest = extentTest.get();

        if (currentExtentTest != null) {

            currentExtentTest.log(
                    Status.FAIL,
                    "Configuration method failed: "
                            + result.getMethod().getMethodName()
            );

            if (result.getThrowable() != null) {
                currentExtentTest.log(
                        Status.FAIL,
                        result.getThrowable()
                );
            }

            return;
        }


        ExtentTest configurationTest = extent.createTest(
                result.getTestClass()
                        .getRealClass()
                        .getSimpleName()
                        + " | Configuration Failure | "
                        + result.getMethod().getMethodName()
        );

        configurationTest.log(
                Status.FAIL,
                "Configuration method failed: "
                        + result.getMethod().getMethodName()
        );

        if (result.getThrowable() != null) {
            configurationTest.log(
                    Status.FAIL,
                    result.getThrowable()
            );
        }
    }

    @Override
    public void onConfigurationSkip(ITestResult result) {

        ExtentTest currentExtentTest = extentTest.get();

        if (currentExtentTest != null) {
            currentExtentTest.log(Status.SKIP,
                    "Configuration skipped: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        try {
            ExtentTest currentExtentTest = getOrCreateExtentTest(result);
            currentExtentTest.log(
                    Status.PASS,
                    "Test Passed"
            );
        } finally {
            extentTest.remove();
            cleanupAttemptCounter(result);
        }


    }

    @Override
    public void onTestFailure(ITestResult result) {

        try {
            ExtentTest currentExtentTest = getOrCreateExtentTest(result);

            if (result.getThrowable() != null) {
                currentExtentTest.fail(result.getThrowable());
            } else {
                currentExtentTest.fail("Test failed without an exception.");
            }


            try {

                LogEntries browserLogs =
                        getDriver().manage()
                                .logs()
                                .get(LogType.BROWSER);

                for (LogEntry log : browserLogs) {

                    if (log.getLevel() == Level.SEVERE) {

                        logger.error(
                                "Browser JS Error: {}",
                                log.getMessage()
                        );


                        currentExtentTest.fail(
                                "Browser JS Error: "
                                        + log.getMessage()
                        );
                    }
                }

            } catch (Exception e) {

                logger.warn(
                        "Browser console logs could not be captured: {}",
                        e.getMessage()
                );
            }


            try {
                String screenshotName =
                        result.getMethod().getMethodName()
                                + "_thread_"
                                + Thread.currentThread().getId()
                                + "_"
                                + System.currentTimeMillis();

                WebDriver driver = getDriver();
                String screenshotPath = ScreenshotUtils.getScreenshot(driver, screenshotName);

                logger.info(
                        "FAILURE SCREENSHOT | Thread: {} | Driver: {}",
                        Thread.currentThread().getId(),
                        System.identityHashCode(driver)
                );


                currentExtentTest.addScreenCaptureFromPath(
                        screenshotPath,
                        "Failure Screenshot"
                );

            } catch (IllegalStateException exception) {


                currentExtentTest.warning(
                        "Screenshot was not captured because the driver "
                                + "was unavailable: "
                                + exception.getMessage()
                );

            } catch (Exception exception) {

                currentExtentTest.warning(
                        "Screenshot capture failed: "
                                + exception.getMessage()
                );

            }
        } finally {
            extentTest.remove();
            if (!Boolean.TRUE.equals(result.getAttribute("retrying"))) {
                cleanupAttemptCounter(result);
            }
        }


    }

    @Override
    public void onTestSkipped(ITestResult result) {

        try {
            ExtentTest currentExtentTest = getOrCreateExtentTest(result);
            currentExtentTest.log(Status.SKIP, "Test Skipped");

            if (result.getThrowable() != null) {
                currentExtentTest.log(
                        Status.SKIP,
                        result.getThrowable()
                );
            }

            boolean retrying =
                    Boolean.TRUE.equals(
                            result.getAttribute("retrying")
                    );

            if (retrying) {
                currentExtentTest.log(
                        Status.INFO,
                        "Test is being retried."
                );
            } else {
                currentExtentTest.log(
                        Status.INFO,
                        "Test skipped without retry."
                );
            }

        } finally {
            extentTest.remove();
            if (!Boolean.TRUE.equals(result.getAttribute("retrying"))) {
                cleanupAttemptCounter(result);
            }
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        extent.flush();
    }

    private void cleanupAttemptCounter(ITestResult result) {

        Object key = result.getAttribute(ATTEMPT_KEY);

        if (key != null) {
            attempts.remove(key.toString());
        }
    }

    private ExtentTest getOrCreateExtentTest(ITestResult result) {

        ExtentTest currentExtentTest = extentTest.get();

        if (currentExtentTest != null) {
            return currentExtentTest;
        }

        String className =
                result.getTestClass()
                        .getRealClass()
                        .getSimpleName();

        String methodName =
                result.getMethod()
                        .getMethodName();

        String browserName =
                getBrowserNameSafely(result);

        ExtentTest fallbackTest = extent.createTest(
                String.format(
                        "%s | %s | [%s]",
                        className,
                        methodName,
                        browserName
                )
        );

        extentTest.set(fallbackTest);

        return fallbackTest;
    }

    private String getBrowserNameSafely(ITestResult result) {

        try {
            WebDriver driver = getDriver();

            if (driver instanceof HasCapabilities capabilities) {
                return capabilities
                        .getCapabilities()
                        .getBrowserName();
            }

        } catch (IllegalStateException e) {
            logger.debug("WebDriver unavailable while resolving browser name: {}", e.getMessage());
        }

        String browserParameter =
                result.getTestContext()
                        .getCurrentXmlTest()
                        .getParameter("browser");

        return browserParameter != null
                ? browserParameter
                : "unknown-browser";
    }
}
