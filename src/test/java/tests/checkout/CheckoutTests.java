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

public class CheckoutTests extends BaseTest {



    @Test(groups = "smoke" , dataProviderClass = TestDataProvider.class, dataProvider = "checkoutData")
    public void navigateFromCartToCheckoutTest(HashMap<String, Object> input) {
        HeaderComponent header = new HeaderComponent(getDriver());
        LoginPage login = header.clickLogin_SignUpButton();
        login.login(Credentials.getEmail(), Credentials.getPassword());
        header.waitUntilLoggedIn();
        ProductListPage plp = header.clickProducts();
        plp.addProductToCart(input.get("product").toString());
        CartPage cart = plp.clickViewCart();
        CheckoutPage checkout = cart.clickCheckout();
        Assert.assertEquals(checkout.getBreadcrumbText(),input.get("breadCrumbText").toString());
        header.clickCart();
        cart.clearCartItems();
    }


}
