package tests.auth;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Credentials;
import java.util.HashMap;

public class LoginTests extends BaseTest {


    @Test(groups = "smoke")
    public void validLoginTest() {
        LoginPage login = new LoginPage(getDriver()).open();
        login.login(Credentials.getEmail(), Credentials.getPassword());
        Assert.assertTrue(login.header().isLogoutButtonDisplayed());
    }

    @Test(groups = "smoke", dataProvider = "invalidLogins", dataProviderClass = TestDataProvider.class)
    public void invalidLoginTest(HashMap<String, String> input) {
        LoginPage login = new LoginPage(getDriver()).open();
        login.login(input.get("email"), input.get("password"));
        Assert.assertEquals(login.getLoginErrorMessage(), input.get("errorMessage"));
    }


}
