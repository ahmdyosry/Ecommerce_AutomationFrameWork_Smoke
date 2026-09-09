package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import org.openqa.selenium.support.ThreadGuard;
import utils.ConfigReader;

import java.util.logging.Level;

import static utils.ConfigReader.getProperty;

public class BaseTest {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    protected void launchApp(@Optional("") String browserParameter) throws IOException {
        initializeDriver(browserParameter);
        getDriver().get(getProperty("baseUrl"));
    }


    @AfterMethod(alwaysRun = true)
    protected void teardown() {

        WebDriver currentDriver = DRIVER.get();

        try {

            if (currentDriver != null) {
                currentDriver.quit();
            }

        } finally {

            DRIVER.remove();

            System.out.println(
                    "Browser closed on thread: "
                            + Thread.currentThread().getId()
            );
        }
    }


    private void initializeDriver(String browserParameter) throws IOException {
        String cliBrowser = System.getProperty("cliBrowser");
        String browserName =
                !browserParameter.isBlank()
                        ? browserParameter
                        : cliBrowser != null && !cliBrowser.isBlank()
                        ? cliBrowser
                        : getProperty("browser");
        String headlessProperty = System.getProperty("headless", getProperty("headless"));
        boolean headless = Boolean.parseBoolean(headlessProperty);


        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2); // don't allow notifications from websites
            options.setExperimentalOption("prefs", prefs);
            LoggingPreferences logs = new LoggingPreferences();
            logs.enable(LogType.BROWSER, Level.ALL);
            options.setCapability("goog:loggingPrefs", logs);

            if (headless) {
                options.addArguments("--headless=new");
            }
            WebDriverManager.chromedriver().setup();
            DRIVER.set(ThreadGuard.protect(new ChromeDriver(options)));

        } else if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            WebDriverManager.firefoxdriver().setup();
            if (headless) {
                options.addArguments("-headless");
            }
            DRIVER.set(ThreadGuard.protect(new FirefoxDriver(options)));
        } else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            WebDriverManager.edgedriver().setup();
            if (headless) {
                options.addArguments("--headless=new");
            }
            DRIVER.set(ThreadGuard.protect(new EdgeDriver(options)));
        } else {
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browserName
            );
        }

        if (headless) {
            getDriver().manage().window().setSize(new Dimension(1920, 1080));
        } else {
            getDriver().manage().window().maximize();
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicitWait"))));

    }

    protected final WebDriver getDriver() {

        WebDriver currentDriver = DRIVER.get();

        if (currentDriver == null) {
            throw new IllegalStateException(
                    "No WebDriver exists for thread: "
                            + Thread.currentThread().getId()
                            + ". Check that @BeforeMethod executed successfully."
            );
        }

        return DRIVER.get();
    }

}
