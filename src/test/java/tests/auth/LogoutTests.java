package tests.auth;

import base.BaseTest;
import components.HeaderComponent;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Credentials;

import java.util.HashMap;

public class LogoutTests extends BaseTest {

    @Test(groups = "smoke")
    public void logoutSuccessTest() {
        HeaderComponent header = new HeaderComponent(getDriver());
        LoginPage login = header.clickLogin_SignUpButton();
        login.login(Credentials.getEmail(), Credentials.getPassword());
        Assert.assertEquals(header.LogoutButtonText(),"Logout");
        header.clickLogoutButton();
        Assert.assertTrue(header.isLogin_SignupButtonDisplayed());
    }
}
