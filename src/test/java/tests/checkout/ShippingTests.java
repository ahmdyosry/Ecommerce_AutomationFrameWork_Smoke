package tests.checkout;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import retry.RetryAnalyzer;
import utils.Credentials;

import java.util.HashMap;
import java.util.List;

public class ShippingTests extends BaseTest {

    @Test(groups = {"smoke", "smokeLoggedIn"}, dataProviderClass = TestDataProvider.class, dataProvider = "checkoutData")
    public void verifyShippingAddressDisplayTest(HashMap<String, Object> input) {
        List<String> shippingAddress = (List<String>) input.get("shippingAddress");
        CheckoutPage checkout = new LoginPage(getDriver()).open()
                .login(Credentials.getEmail(), Credentials.getPassword())
                .header().waitUntilLoggedIn()
                .clickProducts()
                .addProductToCart(input.get("product").toString())
                .clickViewCart()
                .clickCheckout();

        try {
            Assert.assertEquals(checkout.getShippingAddressLines(), shippingAddress, "Shipping address is incorrect");
        } finally {
            checkout.header().clickCart().clearCartItems();
        }

    }
}
