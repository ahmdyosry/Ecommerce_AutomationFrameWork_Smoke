package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CategoryPage extends BasePage {

    private final WebDriver driver;
    public CategoryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

@FindBy(xpath = "//div[@class=\"breadcrumbs\"]//li[2]") private WebElement breadCrumb;
    @FindBy(css=".title.text-center") private WebElement categoryTitle;
    By aToCButtons = By.cssSelector(".add-to-cart");
    @FindBy(css = ".add-to-cart") private List<WebElement> addToCartButtons;

    public String getBreadcrumbText () {
        return getText(breadCrumb);
    }

    public String getCategoryTitle () {
        return getText(categoryTitle);
    }

    public boolean isProductsDisplayed() {
    waitNumberOfElements(aToCButtons, 5);
    return addToCartButtons.size() >= 5;
    }
}
