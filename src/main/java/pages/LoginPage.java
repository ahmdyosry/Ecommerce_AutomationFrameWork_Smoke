package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(name = "email") private WebElement nameField;
    @FindBy(name = "password") private WebElement passwordField;
    @FindBy(css = "button[data-qa='login-button']") private WebElement loginButton;

    @FindBy(css = "input[data-qa='signup-name']") private WebElement signUpNameField;
    @FindBy(css = "input[data-qa='signup-email']") private WebElement signUpEmailField;
    @FindBy(css = "button[data-qa='signup-button']") private WebElement signUpButton;
    @FindBy (xpath = "//form[@action=\"/login\"]/p") private WebElement loginErrorMessage;

    public HomePage login(String email, String password) {
        nameField.sendKeys(email);
        passwordField.sendKeys(password);
        clickOn(loginButton);
        return new HomePage(driver);
    }

    public RegisterationPage initializeSignUp(String name, String email) {
        if (email.equalsIgnoreCase("random_email")) {
            email = "random_email_" + System.currentTimeMillis() + "@example.com";
        }
        signUpNameField.sendKeys(name);
        signUpEmailField.sendKeys(email);
        clickOn(signUpButton);
        return new RegisterationPage(driver);
    }

    public String getLoginErrorMessage() {
        waitToVisible(loginErrorMessage);
        return getText(loginErrorMessage);
    }




}
