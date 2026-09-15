package tests.cart;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import retry.RetryAnalyzer;
import utils.Credentials;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


public class CartTests extends BaseTest {

    @Test(groups = {"smoke", "cartState"}, dataProviderClass = TestDataProvider.class, dataProvider = "productNames", retryAnalyzer = RetryAnalyzer.class)
    public void verifyCartTotalTest(HashMap<String, Object> input) {
        ProductListPage plp = new LoginPage(getDriver()).open()
                .login(Credentials.getEmail(), Credentials.getPassword())
                .header().waitUntilLoggedIn()
                .clickProducts();
        List<String> products = (List<String>) input.get("multipleProducts");
        BigDecimal expectedTotal = plp.getExpectedTotalPriceForMultipleProducts(products);
        CartPage cart = plp.addMultipleProductsToCart(products).header().clickCart();
        BigDecimal actualTotal = cart.calculateCartSubtotal();
        CheckoutPage checkout = cart.clickCheckout();
        BigDecimal checkoutTotal = checkout.getCheckoutSubtotal();


        Assert.assertEquals(actualTotal, expectedTotal, "Sum of cart item totals is incorrect");
        Assert.assertEquals(checkoutTotal, expectedTotal, "Displayed checkout subtotal is incorrect");


    }

    @Test(groups = "smoke")
    public void verifyCartQuantityTest() {
        ProductDetailsPage pdp = new ProductListPage(getDriver()).open().clickViewProduct();
        int requestedQuantity = 4;
        int selectedQuantity = pdp.setProductQuantity(requestedQuantity).getSelectedQuantity();
        Assert.assertEquals(selectedQuantity, requestedQuantity);
        int cartQuantity = pdp.clickAddToCart().clickViewCart().getCartTotalQuantity();
        Assert.assertEquals(cartQuantity, requestedQuantity, "Cart quantity is incorrect");
    }

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void removeCartItemsTest(HashMap<String, Object> input) {
        List<String> products = (List<String>) input.get("multipleProducts");
        String emptyCartText = new ProductListPage(getDriver()).open()
                .addMultipleProductsToCart(products)
                .header()
                .clickCart()
                .clearCartItems()
                .getEmptyCartText();
        Assert.assertEquals(emptyCartText, "Cart is empty!", "Cart is not empty after removing all items");
    }
}
