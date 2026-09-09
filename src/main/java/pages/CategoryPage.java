package pages;

import base.BasePage;
import constants.Routes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

import java.util.List;

public class CategoryPage extends BasePage {

    private final WebDriver driver;

    public CategoryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class=\"breadcrumbs\"]//li[2]")
    private WebElement breadCrumb;
    @FindBy(css = ".title.text-center")
    private WebElement categoryTitle;
    By aToCButtons = By.cssSelector(".add-to-cart");
    @FindBy(css = ".add-to-cart")
    private List<WebElement> addToCartButtons;

    public CategoryPage openMenCategory() {
        goTo(ConfigReader.getProperty("baseUrl") + Routes.MEN_CATEGORY);
        return this;
    }

    public CategoryPage openWomenCategory() {
        goTo(ConfigReader.getProperty("baseUrl") + Routes.WOMEN_CATEGORY);
        return this;
    }

    public CategoryPage openKidsCategory() {
        goTo(ConfigReader.getProperty("baseUrl") + Routes.KIDS_CATEGORY);
        return this;
    }

    public String getBreadcrumbText() {
        return getText(breadCrumb);
    }

    public String getCategoryTitle() {
        return getText(categoryTitle);
    }

    public boolean isProductsDisplayed() {
        waitNumberOfElements(aToCButtons, 5);
        return addToCartButtons.size() >= 5;
    }
}
