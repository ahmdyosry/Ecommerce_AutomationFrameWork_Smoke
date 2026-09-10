package pages;

import base.BasePage;
import constants.Routes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

import java.math.BigDecimal;
import java.util.List;

public class CartPage extends BasePage {

    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h4/a")
    private WebElement productName;
    @FindBy(xpath = "//p[@class='cart_total_price']")
    private List<WebElement> cartItemsTotals;
    @FindBy(css = ".btn.btn-default.check_out")
    private WebElement checkOutButton;
    @FindBy(xpath = "//a[@href=\"/login\"]/u")
    private WebElement loginToCheckoutLink;
    @FindBy(css = ".cart_quantity_delete")
    private List<WebElement> deleteCartItems;
    @FindBy(xpath = "//span[@id=\"empty_cart\"]//b")
    private WebElement emptyCartText;
    @FindBy(css = ".cart_quantity button")
    private List<WebElement> itemQuantityButtons;

    public CartPage open() {
        goTo(ConfigReader.getProperty("baseUrl") + Routes.CART);
        return this;
    }

    public String getProductNameText() {
        return getText(productName);
    }

    public BigDecimal calculateCartSubtotal() {

        return cartItemsTotals.stream().map(WebElement::getText).map(this::convertPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CheckoutPage clickCheckout() {
        clickOn(checkOutButton);
        return new CheckoutPage(driver);
    }

    public CartPage clearCartItems() {

        deleteCartItems.forEach(WebElement::click);
        return this;
    }

    public String getEmptyCartText() {
        return getText(emptyCartText);
    }

    public int cartTotalQuantity() {
        return itemQuantityButtons.stream().map(WebElement::getText).map(String::trim).mapToInt(Integer::parseInt).sum();
    }
}
