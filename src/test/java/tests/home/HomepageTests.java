package tests.home;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomepageTests extends BaseTest {

    @Test (groups = "smoke")
    public void homePageUpTest() {
        HomePage home = new HomePage(getDriver());
        boolean isDisplayed = home.isHomePageDisplayed();
        Assert.assertTrue(isDisplayed);
    }
}
