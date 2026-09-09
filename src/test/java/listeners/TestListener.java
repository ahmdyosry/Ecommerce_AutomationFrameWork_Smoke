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
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reporting.ExtentReportManager;

import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ScreenshotUtils;


public class TestListener extends BaseTest implements ITestListener {      // we implement ITestListener Interface
    private final ExtentReports extent = ExtentReportManager.getReportObject();
    private final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        String browserName = "[" + ((HasCapabilities) getDriver()).getCapabilities().getBrowserName() + "]";
        try {
            ExtentTest test = extent.createTest(result.getName() + " " + browserName);
            extentTest.set(test);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
        extentTest.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest currentExtentTest = extentTest.get();

        if (currentExtentTest != null) {
            currentExtentTest.fail(result.getThrowable());
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

                    if (currentExtentTest != null) {
                        currentExtentTest.fail(
                                "Browser JS Error: "
                                        + log.getMessage()
                        );
                    }
                }
            }

        } catch (Exception e) {

            logger.warn(
                    "Browser console logs could not be captured: "
                            + e.getMessage()
            );
        }


        try {
            String screenshotName =
                    result.getMethod().getMethodName()
                            + "_thread_"
                            + Thread.currentThread().getId()
                            + "_"
                            + System.currentTimeMillis();

            String screenshotPath = ScreenshotUtils.getScreenshot(getDriver(), screenshotName);

            System.out.println(
                    "FAILURE SCREENSHOT | Thread: "
                            + Thread.currentThread().getId()
                            + " | Driver: "
                            + System.identityHashCode(getDriver())
            );

            if (currentExtentTest != null) {
                currentExtentTest.addScreenCaptureFromPath(
                        screenshotPath,
                        "Failure Screenshot"
                );
            }

        } catch (IllegalStateException exception) {

            if (currentExtentTest != null) {
                currentExtentTest.warning(
                        "Screenshot was not captured because the driver "
                                + "was unavailable: "
                                + exception.getMessage()
                );
            }

        } catch (Exception exception) {

            if (currentExtentTest != null) {
                currentExtentTest.warning(
                        "Screenshot capture failed: "
                                + exception.getMessage()
                );
            }
        }
        extentTest.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
        extentTest.remove();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extent.flush();
    }
}
