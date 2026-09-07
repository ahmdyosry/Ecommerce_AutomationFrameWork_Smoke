package tests.cart;

import base.BaseTest;
import components.HeaderComponent;
import constants.Routes;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;
import utils.Credentials;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class AddToCartTests extends BaseTest {


    @Test(groups="smoke")
    public void addToCartTest() {
        HeaderComponent header = new HeaderComponent(getDriver());
        ProductListPage plp = header.clickProducts();
        ProdutDetailsPage pdp = plp.clickViewProduct();
        Assert.assertTrue(pdp.clickAddToCart().isAddedToCartMessageDisplayed());
        Assert.assertEquals(pdp.AddedtoCartMessageText(), "Added!");
    }

    @Test(groups = "smoke" , dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void verifySameProductAddedToCartTest(HashMap<String, Object> input) {
        HeaderComponent header = new HeaderComponent(getDriver());
        ProductListPage plp = header.clickProducts();
        String productNameAdded = plp.addProductToCart(input.get("singleProduct").toString());
        CartPage cart = plp.clickViewCart();
        String productNameInCart = cart.getProductNameText();
        Assert.assertEquals(productNameInCart,productNameAdded);
        cart.clearCartItems();
    }

    @Test (groups="smoke" , dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void verifyCartTotalTest(HashMap<String, Object> input) {
        HeaderComponent header = new HeaderComponent(getDriver());
        LoginPage login = header.clickLogin_SignUpButton();
        login.login(Credentials.getEmail(), Credentials.getPassword());
        header.waitUntilLoggedIn();
        header.clickProducts();
        List<String> products = (List<String>) input.get("multipleProducts");
        ProductListPage plp = new ProductListPage(getDriver());
        BigDecimal expectedTotal = plp.cartExpectedTotalForMultipleProducts(products);
        CartPage cart = new HeaderComponent(getDriver()).clickCart();
        BigDecimal actualTotal = cart.calculateCartSubtotal();
        CheckoutPage checkout = cart.clickCheckout();
        BigDecimal checkoutTotal = checkout.getCartSubtotal();
        Assert.assertEquals(actualTotal, expectedTotal, "Sum of cart item totals is incorrect");
        Assert.assertEquals(checkoutTotal, expectedTotal, "Displayed cart subtotal is incorrect");
        header.clickCart();
        cart.clearCartItems();
    }

    @Test(groups = "smoke" , dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void verifyCartQuantityTest(HashMap<String, Object> input) {
        List<String> products = (List<String>) input.get("multipleProducts");
        HeaderComponent header = new HeaderComponent(getDriver());
        ProductListPage plp = header.clickProducts();
        plp.addMultipleProductsToCart(products);
        CartPage cart = header.clickCart();
        int cartQuantity = cart.cartTotalQuantity();
        Assert.assertEquals(cartQuantity, products.size(), "Cart quantity is incorrect");
        cart.clearCartItems();
    }
}
