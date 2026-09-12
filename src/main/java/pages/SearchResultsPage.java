package pages;

import base.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultsPage extends BasePage {

    private final WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='productinfo text-center']/p")
    private WebElement productName;
    @FindBy(linkText = "View Product")
    private WebElement viewProduct;

    public String getSearchedProductNameText() {
        return getText(productName);
    }

    public ProductDetailsPage clickViewProduct() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", viewProduct);
        clickOn(viewProduct);
        return new ProductDetailsPage(driver);
    }
}
