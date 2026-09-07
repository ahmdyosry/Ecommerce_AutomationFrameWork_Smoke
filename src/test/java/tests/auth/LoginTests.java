package tests.auth;

import base.BaseTest;
import components.HeaderComponent;
import constants.Routes;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;
import utils.Credentials;

import java.util.HashMap;

public class LoginTests extends BaseTest {

    // verify login
    @Test(groups = "smoke")
    public void validLoginTest() {
        HeaderComponent header = new HeaderComponent(getDriver());
        LoginPage login = header.clickLogin_SignUpButton();
        login.login(Credentials.getEmail(), Credentials.getPassword());
        Assert.assertEquals(header.LogoutButtonText(),"Logout");
    }

    @Test(groups="smoke", dataProvider = "invalidLogins" , dataProviderClass = TestDataProvider.class)
    public void invalidLoginTest(HashMap<String, String> input) {
        HeaderComponent header = new HeaderComponent(getDriver());
        LoginPage login = header.clickLogin_SignUpButton();
        login.login(input.get("email"), input.get("password"));
        Assert.assertEquals(login.getLoginErrorMessage(),input.get("errorMessage"));
    }


}
