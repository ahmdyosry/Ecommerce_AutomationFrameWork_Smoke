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

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "checkoutData", retryAnalyzer = RetryAnalyzer.class)
    public void verifyShippingAddressDisplayTest(HashMap<String, Object> input) {
        List<String> shippingAddress = (List<String>) input.get("shippingAddress");
        LoginPage login = new LoginPage(getDriver()).open();
        HomePage home = login.login(Credentials.getEmail(), Credentials.getPassword());
        home.header().waitUntilLoggedIn();
        ProductListPage plp = home.header().clickProducts();
        plp.addProductToCart(input.get("product").toString());
        CartPage cart = plp.clickViewCart();
        CheckoutPage checkout = cart.clickCheckout();
        Assert.assertEquals(checkout.getShippingAddressLines(), shippingAddress, "Shipping address is incorrect");
        checkout.header().clickCart();
        cart.clearCartItems();
    }
}
