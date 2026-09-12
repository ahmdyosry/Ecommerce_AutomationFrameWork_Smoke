package tests.checkout;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.Credentials;

import java.util.HashMap;

public class CheckoutTests extends BaseTest {


    @Test(groups = {"smoke", "smokeLoggedIn"}, dataProviderClass = TestDataProvider.class, dataProvider = "checkoutData")
    public void navigateFromCartToCheckoutTest(HashMap<String, Object> input) {
        HomePage home = new LoginPage(getDriver()).open().login(Credentials.getEmail(), Credentials.getPassword());
        home.header().waitUntilLoggedIn();
        CheckoutPage checkout = home.header()
                .clickProducts()
                .addProductToCart(input.get("product").toString())
                .clickViewCart()
                .clickCheckout();

        try {
            Assert.assertEquals(checkout.getBreadcrumbText(), input.get("breadCrumbText").toString());
        } finally {
            checkout.header().clickCart().clearCartItems();
        }
    }


}
