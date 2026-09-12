package tests.cart;

import base.BaseTest;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.util.HashMap;

public class AddToCartTests extends BaseTest {


    @Test(groups = "smoke")
    public void addToCartTest() {
        ProductDetailsPage pdp = new ProductListPage(getDriver()).open().clickViewProduct();
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
}
