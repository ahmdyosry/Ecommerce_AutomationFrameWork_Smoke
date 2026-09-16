package pages;

import base.BasePage;
import components.FooterComponent;
import components.HeaderComponent;
import constants.Routes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

import java.util.List;

public class HomePage extends BasePage {

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private By addToCartButtonsLocator = By.cssSelector(".add-to-cart");
    @FindBy(xpath = "//a[@href=\"#Men\"]")
    private WebElement menCategory;
    @FindBy(xpath = "//a[@href=\"#Women\"]")
    private WebElement womenCategory;
    @FindBy(xpath = "//a[@href=\"#Kids\"]")
    private WebElement kidsCategory;
    @FindBy(xpath = "//a[text()=\"Tshirts \"]")
    private WebElement menTshirtsCategory;
    @FindBy(css = ".add-to-cart")
    private List<WebElement> addToCartButtons;
    By productCards = By.xpath("//div[@class='product-overlay']");
    By productImages = By.xpath("//img[contains(@src,'/get_product_picture/')]");


    public HomePage open() {
        goTo(ConfigReader.getProperty("baseUrl") + Routes.HOME);
        return this;
    }


    public boolean isHomePageDisplayed() {

        return header().isHomePageLogoDisplayed() && header().isCartButtonDisplayed() && areAllProductCardsDisplayed() && footer().isSubscriptionTextDisplayed();
    }

    public CategoryPage navigateToMenTshirtsCategory() {
        clickOn(menCategory);
        clickOn(menTshirtsCategory);
        return new CategoryPage(driver);
    }

    public boolean areAllProductCardsDisplayed() {

        waitNumberOfElements(productImages,30);
        return driver.findElements(productImages).stream().allMatch(productImage-> {

            WebElement productName = productImage.findElement(By.xpath("../p"));
            WebElement productPrice = productImage.findElement(By.xpath("../h2"));

            return productImage.isDisplayed()
                    && productName.isDisplayed()
                    && !productName.getText().isBlank()
                    && productPrice.isDisplayed()
                    && !productPrice.getText().isBlank();
        });
    }


}
