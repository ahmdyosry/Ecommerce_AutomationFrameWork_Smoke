package tests.e2e;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import retry.RetryAnalyzer;
import utils.Credentials;

import java.util.HashMap;

public class PurchaseFlowE2eTest extends BaseTest {

    @Test(groups = {"smoke" , "smokeLoggedIn"}, dataProviderClass = TestDataProvider.class, dataProvider = "paymentData", retryAnalyzer = RetryAnalyzer.class)
    public void purchaseFlowE2eTest(HashMap<String, String> input) {

        HomePage home = new LoginPage(getDriver()).open()
                .login(Credentials.getEmail(), Credentials.getPassword());
        Assert.assertTrue(home.header().isLogoutButtonDisplayed());

        ProductListPage plp = home.header()
                .clickProducts()
                .addProductToCart(input.get("product"));
        Assert.assertTrue(plp.isAddedToCartMessageDisplayed());
        Assert.assertEquals(plp.getAddedtoCartMessageText(), "Added!");

        CartPage cart = plp.clickViewCart();
        String productNameInCart = cart.getProductNameText();
        Assert.assertEquals(productNameInCart, input.get("product"));

        CheckoutPage checkout = cart.clickCheckout();
        Assert.assertTrue(checkout.isCheckoutPageDisplayed());
        Assert.assertEquals(checkout.getBreadcrumbText(), "Checkout");

        OrderConfirmationPage order = checkout.clickPlaceOrderBtn()
                .pay(input.get("nameOnCard"), input.get("cardNumber"), input.get("cvc"), input.get("expiryMonth"), input.get("expiryYear"));
        Assert.assertEquals(order.getOrderPlacedMessage(), input.get("successMessage"));


    }


}
