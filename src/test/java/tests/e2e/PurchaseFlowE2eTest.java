package tests.e2e;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.Credentials;

import java.util.HashMap;

public class PurchaseFlowE2eTest extends BaseTest {

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "paymentData")
    public void purchaseFlowE2eTest(HashMap<String, String> input) {

        LoginPage login = new LoginPage(getDriver()).open();
        HomePage home = login.login(Credentials.getEmail(), Credentials.getPassword());
        Assert.assertTrue(home.header().isLogoutButtonDisplayed());

        ProductListPage plp = home.header().clickProducts();
        String productNameAdded = plp.addProductToCart(input.get("product"));
        Assert.assertTrue(plp.isAddedToCartMessageDisplayed());
        Assert.assertEquals(plp.addedtoCartMessageText(), "Added!");

        CartPage cart = plp.clickViewCart();
        String productNameInCart = cart.getProductNameText();
        Assert.assertEquals(productNameInCart, productNameAdded);

        CheckoutPage checkout = cart.clickCheckout();
        Assert.assertTrue(checkout.isCheckoutPageDisplayed());
        Assert.assertEquals(checkout.getBreadcrumbText(),"Checkout");

        PaymentPage payment = checkout.clickPlaceOrderBtn();
        OrderConfirmationPage order = payment.pay(input.get("nameOnCard"), input.get("cardNumber"), input.get("cvc"), input.get("expiryMonth"), input.get("expiryYear"));
        Assert.assertEquals(order.getOrderPlacedMessage(), input.get("successMessage"));


    }


}
