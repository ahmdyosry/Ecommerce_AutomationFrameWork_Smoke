package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Optional;

public class RegisterationPage extends BasePage {
    private final WebDriver driver;

    public RegisterationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "id_gender1") private WebElement genderMrRadioButton;
    @FindBy(id = "id_gender2") private WebElement genderMrsRadioButton;
    @FindBy(id = "name") private WebElement nameField;
    @FindBy(id = "password") private WebElement passwordField;
    @FindBy(id="days") private WebElement daysDropdown;
    @FindBy(id="months") private WebElement monthsDropdown;
    @FindBy(id="years") private WebElement yearsDropdown;
    @FindBy(id="newsletter") private WebElement newsletterCheckbox;
    @FindBy(id="optin") private WebElement optinCheckbox;
    @FindBy(id="first_name") private WebElement firstNameField;
    @FindBy(id="last_name") private WebElement lastNameField;
    @FindBy(id="company") private WebElement companyField;
    @FindBy(id="address1") private WebElement address1Field;
    @FindBy(id="address2") private WebElement address2Field;
    @FindBy(id="country") private WebElement countryDropdown;
    @FindBy(id="state") private WebElement stateField;
    @FindBy(id="city") private WebElement cityField;
    @FindBy(id="zipcode") private WebElement zipcodeField;
    @FindBy(id="mobile_number") private WebElement mobileNumberField;
    @FindBy(xpath = "//button[text()='Create Account']") private WebElement createAccount;

    public SuccessRegisterPage fillRegistrationForm(String name, String password, String firstName, String lastName, Optional<String> company,
                                     String address1, Optional<String> address2, String state, String city,
                                     String zipcode, String mobileNumber) {
        clickOn(genderMrRadioButton);
        nameField.sendKeys(name);
        passwordField.sendKeys(password);
        Select days = new Select(daysDropdown);
        days.selectByValue("24");
        Select months = new Select(monthsDropdown);
        months.selectByVisibleText("March");
        Select years = new Select(yearsDropdown);
        years.selectByValue("1992");
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        companyField.sendKeys(company.orElse(""));
        address1Field.sendKeys(address1);
        address2Field.sendKeys(address2.orElse(""));
        Select country = new Select(countryDropdown);
        country.selectByValue("Canada");
        stateField.sendKeys(state);
        cityField.sendKeys(city);
        zipcodeField.sendKeys(zipcode);
        mobileNumberField.sendKeys(mobileNumber);
        clickOn(createAccount);
        return new SuccessRegisterPage(driver);
    }






}
