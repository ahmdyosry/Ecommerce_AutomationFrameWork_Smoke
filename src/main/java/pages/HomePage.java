package pages;

import base.BasePage;
import components.FooterComponent;
import components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    private final WebDriver driver;
    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

private By addToCartButtons = By.cssSelector(".add-to-cart");
    @FindBy(xpath = "//a[@href=\"#Men\"]") private WebElement menCategory;
    @FindBy(xpath = "//a[@href=\"#Women\"]") private WebElement womenCategory;
    @FindBy(xpath = "//a[@href=\"#Kids\"]") private WebElement kidsCategory;
    @FindBy(xpath="//a[text()=\"Tshirts \"]") private WebElement menTshirtsCategory;



    public boolean isHomePageDisplayed() {

        waitNumberOfElements(addToCartButtons,20);
        return header().isHomePageLogoDisplayed() && header().isCartButtonDisplayed() && footer().isSubscriptionTextDisplayed();
    }

    public CategoryPage navigateToMenTshirtsCategory() {
        clickOn(menCategory);
        clickOn(menTshirtsCategory);
        return new CategoryPage(driver);
    }




}
