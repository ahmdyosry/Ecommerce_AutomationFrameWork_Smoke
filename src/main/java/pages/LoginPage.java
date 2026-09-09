package pages;

import base.BasePage;
import constants.Routes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

public class LoginPage extends BasePage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "email")
    private WebElement nameField;
    @FindBy(name = "password")
    private WebElement passwordField;
    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//form[@action=\"/login\"]/p")
    private WebElement loginErrorMessage;


    public LoginPage open() {
        goTo(ConfigReader.getProperty("baseUrl") + Routes.LOGIN);
        return this;
    }

    public HomePage login(String email, String password) {
        nameField.sendKeys(email);
        passwordField.sendKeys(password);
        clickOn(loginButton);
        return new HomePage(driver);
    }

    public String getLoginErrorMessage() {
        return getText(loginErrorMessage);
    }


}
