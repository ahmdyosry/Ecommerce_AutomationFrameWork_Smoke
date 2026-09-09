package tests.cart;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.Credentials;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class AddToCartTests extends BaseTest {


    @Test(groups = "smoke")
    public void addToCartTest() {
        ProductListPage plp = new ProductListPage(getDriver()).open();
        ProdutDetailsPage pdp = plp.clickViewProduct();
        Assert.assertTrue(pdp.clickAddToCart().isAddedToCartMessageDisplayed());
        Assert.assertEquals(pdp.addedtoCartMessageText(), "Added!");
    }

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void verifySameProductAddedToCartTest(HashMap<String, Object> input) {
        ProductListPage plp = new ProductListPage(getDriver()).open();
        String productNameAdded = plp.addProductToCart(input.get("singleProduct").toString());
        CartPage cart = plp.clickViewCart();
        String productNameInCart = cart.getProductNameText();
        Assert.assertEquals(productNameInCart, productNameAdded);
        cart.clearCartItems();
    }

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void verifyCartTotalTest(HashMap<String, Object> input) {
        LoginPage login = new LoginPage(getDriver()).open();
        HomePage home = login.login(Credentials.getEmail(), Credentials.getPassword());
        home.header().waitUntilLoggedIn();
        ProductListPage plp = home.header().clickProducts();
        List<String> products = (List<String>) input.get("multipleProducts");
        BigDecimal expectedTotal = plp.cartExpectedTotalForMultipleProducts(products);
        CartPage cart = plp.header().clickCart();
        BigDecimal actualTotal = cart.calculateCartSubtotal();
        CheckoutPage checkout = cart.clickCheckout();
        BigDecimal checkoutTotal = checkout.getCartSubtotal();
        Assert.assertEquals(actualTotal, expectedTotal, "Sum of cart item totals is incorrect");
        Assert.assertEquals(checkoutTotal, expectedTotal, "Displayed cart subtotal is incorrect");
        checkout.header().clickCart();
        cart.clearCartItems();
    }

    @Test(groups = "smoke", dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void verifyCartQuantityTest(HashMap<String, Object> input) {
        List<String> products = (List<String>) input.get("multipleProducts");
        ProductListPage plp = new ProductListPage(getDriver()).open();
        plp.addMultipleProductsToCart(products);
        CartPage cart = plp.header().clickCart();
        int cartQuantity = cart.cartTotalQuantity();
        Assert.assertEquals(cartQuantity, products.size(), "Cart quantity is incorrect");
        cart.clearCartItems();
    }
}
