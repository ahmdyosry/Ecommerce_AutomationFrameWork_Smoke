package tests.checkout;

import base.BaseTest;
import components.HeaderComponent;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductListPage;
import retry.RetryAnalyzer;
import utils.Credentials;

import java.util.HashMap;
import java.util.List;

public class ShippingTests extends BaseTest {

    @Test(groups = "smoke" , dataProviderClass = TestDataProvider.class, dataProvider = "checkoutData", retryAnalyzer = RetryAnalyzer.class)
    public void verifyShippingAddressDisplayTest(HashMap<String, Object> input) {
        List<String> shippingAddress = (List<String>) input.get("shippingAddress");
        HeaderComponent header = new HeaderComponent(getDriver());
        LoginPage login = header.clickLogin_SignUpButton();
        login.login(Credentials.getEmail(), Credentials.getPassword());
        header.waitUntilLoggedIn();
        ProductListPage plp = header.clickProducts();
        plp.addProductToCart(input.get("product").toString());
        CartPage cart = plp.clickViewCart();
        CheckoutPage checkout = cart.clickCheckout();
        Assert.assertEquals(checkout.getShippingAddressLines(), shippingAddress, "Shipping address is incorrect");
        header.clickCart();
        cart.clearCartItems();
    }
}
