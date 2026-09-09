package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.module.Network;
import org.openqa.selenium.bidi.module.Script;
import org.openqa.selenium.bidi.network.AddInterceptParameters;
import org.openqa.selenium.bidi.network.InterceptPhase;
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
import java.util.List;
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
        blockAds();
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
            prefs.put("profile.default_content_setting_values.popups", 2);
            options.setExperimentalOption("prefs", prefs);
            options.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
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
            options.enableBiDi();
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

    private void blockAds() {

        final List<String> AD_DOMAINS = List.of(
                "*://*.doubleclick.net/*",
                "*://*.googlesyndication.com/*",
                "*://*.googleadservices.com/*",
                "*://*.googletagservices.com/*",
                "*://*.adservice.google.com/*",
                "*://*.flashtalking.com/*",
                "*://*.brevolinks.com/*",
                "*://*.brevo.com/*",
                "*://*.brevosend.com/*",
                "*://*.sendinblue.com/*",
                "*://*.adnxs.com/*",
                "*://*.adsrvr.org/*",
                "*://*.criteo.com/*",
                "*://*.criteo.net/*",
                "*://*.taboola.com/*",
                "*://*.outbrain.com/*",
                "*://*.amazon-adsystem.com/*",
                "*://*.pubmatic.com/*",
                "*://*.rubiconproject.com/*",
                "*://*.openx.net/*",
                "*://*.casalemedia.com/*",
                "*://*.quantserve.com/*",
                "*://*.scorecardresearch.com/*",
                "*://*.zedo.com/*"
        );

        String removeFrames = """
        () => {

            const removeAds = () => {

                // Remove Google fullscreen/vignette ad containers
                document.querySelectorAll(
                    "ins[data-vignette-loaded='true']"
                ).forEach(ad => ad.remove());

                // Remove any remaining iframes
                document.querySelectorAll('iframe')
                    .forEach(frame => frame.remove());
            };

            removeAds();

            new MutationObserver(removeAds)
                .observe(document, {
                    childList: true,
                    subtree: true,
                    attributes: true,
                    attributeFilter: [
                        'data-vignette-loaded',
                        'class',
                        'style'
                    ]
                });
        }
        """;


        if (getDriver() instanceof ChromeDriver chromeDriver) {

            chromeDriver.executeCdpCommand(
                    "Network.enable",
                    Map.of()
            );

            chromeDriver.executeCdpCommand(
                    "Network.setBlockedURLs",
                    Map.of("urls",AD_DOMAINS));

            chromeDriver.executeCdpCommand(
                    "Page.addScriptToEvaluateOnNewDocument",
                    Map.of("source", "(" + removeFrames + ")();")
            );
        }

        else if (getDriver() instanceof FirefoxDriver) {

            Network network = new Network(getDriver());

            network.addIntercept(
                    new AddInterceptParameters(
                            InterceptPhase.BEFORE_REQUEST_SENT
                    )
            );

            network.onBeforeRequestSent(event -> {

                String url = event.getRequest().getUrl();

                boolean isAd = AD_DOMAINS.stream()
                        .anyMatch(url::contains);

                if (isAd) {
                    network.failRequest(
                            event.getRequest().getRequestId()
                    );
                }
            });

            Script script = new Script(getDriver());

            script.addPreloadScript(removeFrames);
        }
    }

}
