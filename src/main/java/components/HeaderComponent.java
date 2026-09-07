package components;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductListPage;

public class HeaderComponent extends BasePage {

    private WebDriver driver;
    public HeaderComponent(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = "img[alt='Website for automation practice']") private WebElement homePageLogo;
    @FindBy(css = ".fa-home") private WebElement homeButton;
    @FindBy(css = ".card_travel") private WebElement productsButton;
    @FindBy(css = "a[href='/view_cart'] .fa-shopping-cart") private WebElement cartButton;
    @FindBy(xpath="//a[text()=' Signup / Login']") private WebElement login_SignUpButton;
    @FindBy(xpath = "//a[text()=' Logout']") private WebElement logoutButton;

    public boolean isCartButtonDisplayed () {
        waitToVisible(cartButton);
        return cartButton.isDisplayed();
    }

    public boolean isHomePageLogoDisplayed () {
        waitToVisible(homePageLogo);
        return homePageLogo.isDisplayed();
    }

    public String LogoutButtonText() {
        waitToVisible(logoutButton);
        return getText(logoutButton);
    }

    public LoginPage clickLogin_SignUpButton() {
        clickOn(login_SignUpButton);
        return new LoginPage(driver);
    }

    public LoginPage clickLogoutButton() {
        clickOn(logoutButton);
        return new LoginPage(driver);
    }

    public ProductListPage clickProducts() {
        clickOn(productsButton);
        return new ProductListPage(driver);
    }

    public CartPage clickCart() {
        clickOn(cartButton);
        return new CartPage(driver);
    }

    public boolean isLogin_SignupButtonDisplayed() {
        waitToVisible(login_SignUpButton);
        return login_SignUpButton.isDisplayed();
    }

    public void waitUntilLoggedIn() {
    waitToVisible(logoutButton);
    }




}
