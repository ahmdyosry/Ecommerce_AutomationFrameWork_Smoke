package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentPage extends BasePage {

    private final WebDriver driver;

    public PaymentPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "name_on_card")
    private WebElement nameOnCardField;
    @FindBy(name = "card_number")
    private WebElement cardNumberField;
    @FindBy(name = "cvc")
    private WebElement cvcField;
    @FindBy(name = "expiry_month")
    private WebElement expiryMonthField;
    @FindBy(name = "expiry_year")
    private WebElement expiryYearField;
    @FindBy(id = "submit")
    private WebElement payButton;

    public OrderConfirmationPage pay(String nameOnCard, String cardNumber, String cvc, String expiryMonth, String expiryYear) {
        scrollToElement(payButton);
        nameOnCardField.sendKeys(nameOnCard);
        cardNumberField.sendKeys(cardNumber);
        cvcField.sendKeys(cvc);
        expiryMonthField.sendKeys(expiryMonth);
        expiryYearField.sendKeys(expiryYear);
        clickOn(payButton);
        return new OrderConfirmationPage(driver);
    }


}
