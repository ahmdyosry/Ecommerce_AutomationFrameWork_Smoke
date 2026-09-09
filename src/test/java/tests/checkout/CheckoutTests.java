package tests.checkout;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.Credentials;
import java.util.HashMap;

public class CheckoutTests extends BaseTest {


    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "checkoutData")
    public void navigateFromCartToCheckoutTest(HashMap<String, Object> input) {
        LoginPage login = new LoginPage(getDriver()).open();
        HomePage home = login.login(Credentials.getEmail(), Credentials.getPassword());
        home.header().waitUntilLoggedIn();
        ProductListPage plp = home.header().clickProducts();
        plp.addProductToCart(input.get("product").toString());
        CartPage cart = plp.clickViewCart();
        CheckoutPage checkout = cart.clickCheckout();
        Assert.assertEquals(checkout.getBreadcrumbText(), input.get("breadCrumbText").toString());
        checkout.header().clickCart();
        cart.clearCartItems();
    }


}
