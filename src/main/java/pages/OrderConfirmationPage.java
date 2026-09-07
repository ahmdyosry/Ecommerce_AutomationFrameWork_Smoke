package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPage extends BasePage {

    private final WebDriver driver;

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h2[@data-qa=\"order-placed\"]/b") private WebElement orderPlacedMessage;

    public String getOrderPlacedMessage() {
        return getText(orderPlacedMessage);
    }
}
