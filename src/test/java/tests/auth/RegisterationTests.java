package tests.auth;

import base.BaseTest;
import components.HeaderComponent;
import constants.Routes;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterationPage;
import pages.SuccessRegisterPage;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Optional;

public class RegisterationTests extends BaseTest {

    @Test(groups = "smoke", dataProvider = "registerFormData" , dataProviderClass = TestDataProvider.class)
    public void successfulUserRegistration(HashMap<String, String> input) {
        HeaderComponent header = new HeaderComponent(getDriver());
        LoginPage login = header.clickLogin_SignUpButton();
        RegisterationPage registeration = login.initializeSignUp(input.get("name"), input.get("email"));
        SuccessRegisterPage success = registeration.fillRegistrationForm(
                input.get("name"),
                input.get("password"),
                input.get("firstName"),
                input.get("lastName"),
                Optional.ofNullable(input.get("company")),
                input.get("address1"),
                Optional.ofNullable(input.get("address2")),
                input.get("state"),
                input.get("city"),
                input.get("zipcode"),
                input.get("mobileNumber")
        );

        Assert.assertEquals(success.getSuccessMessage(), input.get("successMessage"));
        // registeration.signUp(input.get("name"), input.get("email"));
        //HeaderComponent header = new HeaderComponent(getDriver());
        //Assert.assertEquals(header.LogoutButtonText(),"Logout");
    }
}
