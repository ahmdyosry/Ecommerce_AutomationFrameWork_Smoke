package base;

import components.FooterComponent;
import components.HeaderComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.math.BigDecimal;
import java.time.Duration;

public class BasePage {

    private final WebDriver driver;

    public BasePage (WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(css = ".continue-prompt-text") private WebElement closeAd;

    public HeaderComponent header() {
        return new HeaderComponent(driver);
    }

    public FooterComponent footer() {
        return new FooterComponent(driver);
    }


/* ================================================================================
                               WAITS
  ================================================================================= */

    protected void waitToVisible (WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicitWait"))));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitToInvisible (WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicitWait"))));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitToBeClickable (WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicitWait"))));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitStaleness (WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicitWait"))));
        wait.until(ExpectedConditions.stalenessOf(element));

    }

    protected void waitUrlChange(String oldUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicitWait"))));
        wait.until(driver -> !driver.getCurrentUrl().equals(oldUrl));
    }

    protected void waitNumberOfElements (By locator, int number) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicitWait"))));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator,number));
    }

    protected boolean waitForOptionalElement(WebElement element, int seconds) {
        try {
            WebDriverWait shortWait =
                    new WebDriverWait(driver, Duration.ofSeconds(seconds));

            shortWait.until(
                    ExpectedConditions.visibilityOf(element)
            );

            return true;

        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean closeAdIfAppears() {

        try {
            WebElement shortWait = new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(closeAd));

            closeAd.click();
            return true;

        } catch (TimeoutException e) {
            return false;
        }
    }

/* ================================================================================
                               NAVIGATION HELPERS
  ================================================================================= */


    protected void goTo(String url) {
        driver.get(url);
    }

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void goBack() {
        driver.navigate().back();
    }

    protected void goForward() {
        driver.navigate().forward();
    }

    /* ================================================================================
                               Click, GetText, GetAttribute
  ================================================================================= */

    protected String getText(WebElement element) {
        waitToVisible(element);
        return element.getText().trim();
    }

    protected void clickOn(WebElement element) {
        waitToBeClickable(element);
        element.click();
    }

    protected BigDecimal convertPrice(String priceText) {

        String numericValue = priceText
                .replaceAll("[^0-9.,]", "")
                .replaceAll("^[.,]+", "")
                .replace(",", "");

        return new BigDecimal(numericValue);
    }


    /* ================================================================================
                               Scroll Helpers
  ================================================================================= */

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'auto', block:'center', inline:'nearest'});",
                element
        );
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, 0);"
        );
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );
    }

    protected void scrollBy(int x, int y) {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollBy(arguments[0], arguments[1]);",
                x,
                y
        );
    }
}