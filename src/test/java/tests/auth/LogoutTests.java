package tests.auth;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.Credentials;

public class LogoutTests extends BaseTest {

    @Test(groups = "smoke")
    public void logoutSuccessTest() {
        HomePage home = new LoginPage(getDriver()).open().login(Credentials.getEmail(), Credentials.getPassword());
        Assert.assertTrue(home.header().isLogoutButtonDisplayed());
        home.header().clickLogoutButton();
        Assert.assertTrue(home.header().isLogin_SignupButtonDisplayed());
    }
}
