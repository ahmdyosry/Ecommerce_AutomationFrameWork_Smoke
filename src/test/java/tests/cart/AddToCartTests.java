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

public class AddToCartTests extends BaseTest {


    @Test(groups = "smoke")
    public void addToCartTest() {
        ProdutDetailsPage pdp = new ProductListPage(getDriver()).open().clickViewProduct();
        try {
            Assert.assertTrue(pdp.clickAddToCart().isAddedToCartMessageDisplayed());
            Assert.assertEquals(pdp.getAddedtoCartMessageText(), "Added!");
        } finally {
            pdp.clickViewCart().clearCartItems();
        }
    }

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void verifySameProductAddedToCartTest(HashMap<String, Object> input) {
        String productNameInCart = new ProductListPage(getDriver()).open()
                .addProductToCart(input.get("singleProduct").toString())
                .clickViewCart()
                .getProductNameText();
        try {
            Assert.assertEquals(productNameInCart, input.get("singleProduct").toString());
        } finally {
            new CartPage(getDriver()).clearCartItems();
        }
    }

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "productNames", retryAnalyzer = RetryAnalyzer.class)
    public void verifyCartTotalTest(HashMap<String, Object> input) {
        ProductListPage plp = new LoginPage(getDriver()).open()
                .login(Credentials.getEmail(), Credentials.getPassword())
                .header().waitUntilLoggedIn()
                .clickProducts();
        List<String> products = (List<String>) input.get("multipleProducts");
        BigDecimal expectedTotal = plp.cartExpectedTotalForMultipleProducts(products);
        CartPage cart = plp.header().clickCart();
        BigDecimal actualTotal = cart.calculateCartSubtotal();
        CheckoutPage checkout = cart.clickCheckout();
        BigDecimal checkoutTotal = checkout.getCartSubtotal();
        try {
            Assert.assertEquals(actualTotal, expectedTotal, "Sum of cart item totals is incorrect");
            Assert.assertEquals(checkoutTotal, expectedTotal, "Displayed cart subtotal is incorrect");
        } finally {
            checkout.header().clickCart().clearCartItems();
        }
    }

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void verifyCartQuantityTest(HashMap<String, Object> input) {
        List<String> products = (List<String>) input.get("multipleProducts");
        CartPage cart = new ProductListPage(getDriver()).open()
                .addMultipleProductsToCart(products)
                .header()
                .clickCart();
        int cartQuantity = cart.cartTotalQuantity();
        try {
            Assert.assertEquals(cartQuantity, products.size(), "Cart quantity is incorrect");
        } finally {
            cart.clearCartItems();
        }
    }
}
