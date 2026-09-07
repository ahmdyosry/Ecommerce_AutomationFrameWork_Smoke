package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.util.List;

public class ProductListPage extends BasePage {

    private final WebDriver driver;

    public ProductListPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "search_product") private WebElement searchProduct;
    @FindBy(css = ".fa-search") private WebElement submitSearch;
    @FindBy(linkText = "View Product") private WebElement viewProduct;
    @FindBy(xpath = "//div[@class='productinfo text-center']/p") private List<WebElement> productNames;
    @FindBy(xpath = "//div[@class='productinfo text-center']/h2") private List<WebElement> productPrices;

    @FindBy(css = ".btn.btn-default.add-to-cart") private List<WebElement> addToCartButtons;
    @FindBy(xpath = "//div[@class=\"modal-header\"]/h4") private WebElement addedtoCartMessage;
    @FindBy(xpath = "//div[@class=\"modal-body\"]//u") private WebElement viewCartButton;
    @FindBy(xpath = "//button[text()='Continue Shopping']") private WebElement continueShoppingButton;


    public SearchResultsPage searchProduct(String productName) {
        searchProduct.sendKeys(productName);
        clickOn(submitSearch);
        return new SearchResultsPage(driver);
    }

    public ProdutDetailsPage clickViewProduct() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", viewProduct);
        clickOn(viewProduct);
        return new ProdutDetailsPage(driver);
    }

    public String addProductToCart(String productName) {
        WebElement productNameLocator = getProductName(productName);
        scrollToElement(productNameLocator);
        WebElement addToCart = productNameLocator.findElement(By.xpath("../a"));
        new Actions(driver).moveToElement(addToCart).perform();
        clickOn(addToCart);
        return productName;
    }

    public CartPage clickViewCart() {
        clickOn(viewCartButton);
        return new CartPage(driver);
    }

    public WebElement getProductName(String productName) {

        return productNames.stream().filter(name -> getText(name).equalsIgnoreCase(productName)).findFirst().orElseThrow(() -> new RuntimeException("Product not found: " + productName));
    }

    public BigDecimal getProductPrice(String productName) {

        String priceText = getText(getProductName(productName).findElement(By.xpath("../h2")));

        return convertPrice(priceText);
    }

    public void continueShopping() {
        clickOn(continueShoppingButton);
    }

    public BigDecimal cartExpectedTotalForMultipleProducts(List<String> products) {
        return products.stream().map(productName -> {

                    BigDecimal price = getProductPrice(productName);
                    addProductToCart(productName);
                    continueShopping();

                    return price;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addMultipleProductsToCart(List<String> products) {
        products.forEach(productName -> {
            addProductToCart(productName);
            continueShopping();
        });
    }


}
