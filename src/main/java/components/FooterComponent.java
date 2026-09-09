package components;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FooterComponent extends BasePage {

    private final WebDriver driver;

    public FooterComponent(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h2[contains(text(),'Subscription')]")
    private WebElement subscriptionText;

    public boolean isSubscriptionTextDisplayed() {
        return checkIfDisplayed(subscriptionText);
    }
}
