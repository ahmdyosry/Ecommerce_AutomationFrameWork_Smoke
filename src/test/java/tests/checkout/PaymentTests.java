package tests.checkout;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.Credentials;

import java.util.HashMap;

public class PaymentTests extends BaseTest {

    @Test(groups = {"smoke", "smokeLoggedIn"}, dataProviderClass = TestDataProvider.class, dataProvider = "paymentData")
    public void verifySuccessPaymentTest(HashMap<String, String> input) {
        HomePage home = new LoginPage(getDriver()).open().login(Credentials.getEmail(), Credentials.getPassword());
        OrderConfirmationPage order = home.header().waitUntilLoggedIn()
                .clickProducts()
                .addProductToCart(input.get("product"))
                .clickViewCart()
                .clickCheckout()
                .clickPlaceOrderBtn()
                .pay(input.get("nameOnCard"), input.get("cardNumber"), input.get("cvc"), input.get("expiryMonth"), input.get("expiryYear"));


        Assert.assertEquals(order.getOrderPlacedMessage(), input.get("successMessage"));

    }
}
