package tests.checkout;

import base.BaseTest;
import components.HeaderComponent;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.Credentials;

import java.util.HashMap;

public class PaymentTests extends BaseTest {

    @Test(groups = "smoke" , dataProviderClass = TestDataProvider.class, dataProvider = "paymentData")
    public void verifySuccessPaymentTest(HashMap<String, String> input) {
        HeaderComponent header = new HeaderComponent(getDriver());
        LoginPage login = header.clickLogin_SignUpButton();
        login.login(Credentials.getEmail(), Credentials.getPassword());
        header.waitUntilLoggedIn();
        ProductListPage plp = header.clickProducts();
        plp.addProductToCart(input.get("product"));
        CartPage cart = plp.clickViewCart();
        CheckoutPage checkout = cart.clickCheckout();
        PaymentPage payment = checkout.clickPlaceOrderBtn();
        OrderConfirmationPage order = payment.pay(input.get("nameOnCard"), input.get("cardNumber"), input.get("cvc"), input.get("expiryMonth"), input.get("expiryYear"));
        Assert.assertEquals(order.getOrderPlacedMessage(),input.get("successMessage"));
        header.clickCart();
        cart.clearCartItems();
    }
}
