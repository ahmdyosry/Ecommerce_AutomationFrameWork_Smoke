package tests.cart;

import base.BaseTest;
import components.HeaderComponent;
import dataproviders.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductListPage;

import java.util.HashMap;
import java.util.List;

public class RemoveFromCartTests extends BaseTest {

    @Test(groups = "smoke" , dataProviderClass = TestDataProvider.class, dataProvider = "productNames")
    public void removeCartItemsTest(HashMap<String, Object> input) {
        List<String> products = (List<String>) input.get("multipleProducts");
        HeaderComponent header = new HeaderComponent(getDriver());
        ProductListPage plp = header.clickProducts();
        plp.addMultipleProductsToCart(products);
        CartPage cart = header.clickCart();
        cart.clearCartItems();
        String emptyCartText = cart.emptyCartText();
        Assert.assertEquals(emptyCartText, "Cart is empty!", "Cart is not empty after removing all items");
    }
}
