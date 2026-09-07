package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutPage extends BasePage {

    private final WebDriver driver;
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "(//p[@class='cart_total_price'])[last()]") private WebElement cartSubtotal;
    @FindBy(xpath = "//div[@class=\"breadcrumbs\"]//li[2]") private WebElement breadCrumb;
    @FindBy(css = "#address_delivery li:not(:first-child)") private List<WebElement> shippingAddressLines;
    @FindBy(css = ".btn.btn-default.check_out") private WebElement placeOrderBtn;


    public BigDecimal getCartSubtotal() {
        return convertPrice(getText(cartSubtotal));
    }

    public String getBreadcrumbText () {
        return getText(breadCrumb);
    }

    public List<String> getShippingAddressLines() {
        return shippingAddressLines.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .toList();
    }

    public PaymentPage clickPlaceOrderBtn() {
        clickOn(placeOrderBtn);
        return new PaymentPage(driver);
    }
}
