package pages;

import base.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage extends BasePage {
    private final WebDriver driver;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class=\"product-information\"]/h2")
    private WebElement productName;
    @FindBy(xpath = "//div[@class=\"product-information\"]/span/span")
    private WebElement productPrice;
    @FindBy(css = ".btn-default.cart")
    private WebElement addToCartButton;
    @FindBy(xpath = "//div[@class=\"modal-header\"]/h4")
    private WebElement addedtoCartMessage;
    @FindBy(xpath = "//div[@class=\"modal-body\"]//u")
    private WebElement viewCartButton;
    @FindBy(id = "quantity")
    private WebElement quantityInput;

    public String getProductNameText() {
        return getText(productName);
    }

    public boolean isProductPriceDisplayed() {
        return checkIfDisplayed(productPrice);
    }

    public boolean isProductNameDisplayed() {
        return checkIfDisplayed(productName);
    }

    public ProductDetailsPage clickAddToCart() {
        clickOn(addToCartButton);
        return this;
    }

    public boolean isAddedToCartMessageDisplayed() {
        return checkIfDisplayed(addedtoCartMessage);
    }

    public String getAddedtoCartMessageText() {
        return getText(addedtoCartMessage);
    }

    public CartPage clickViewCart() {
        clickOn(viewCartButton);
        return new CartPage(driver);
    }

    public ProductDetailsPage setProductQuantity(int qty) {
        while (Integer.parseInt(quantityInput.getDomProperty("value")) < qty) {
            clickOn(quantityInput);
            quantityInput.sendKeys(Keys.ARROW_UP);
        }
        return this;
    }

    public int getSelectedQuantity() {
        return Integer.parseInt(quantityInput.getDomProperty("value"));
    }
}
