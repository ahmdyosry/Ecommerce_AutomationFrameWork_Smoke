package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProdutDetailsPage extends BasePage {
    private final WebDriver driver;

    public ProdutDetailsPage(WebDriver driver) {
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

    public String getProductNameText() {
        return getText(productName);
    }

    public boolean isProductPriceDisplayed() {
        return checkIfDisplayed(productPrice);
    }

    public boolean isProductNameDisplayed() {
        return checkIfDisplayed(productName);
    }

    public ProdutDetailsPage clickAddToCart() {
        clickOn(addToCartButton);
        return this;
    }

    public boolean isAddedToCartMessageDisplayed() {
        return checkIfDisplayed(addedtoCartMessage);
    }

    public String addedtoCartMessageText() {
        return getText(addedtoCartMessage);
    }
}
