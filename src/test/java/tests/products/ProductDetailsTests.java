package tests.products;

import base.BaseTest;
import components.HeaderComponent;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductListPage;
import pages.ProdutDetailsPage;

public class ProductDetailsTests extends BaseTest {

    @Test (groups = "smoke")
    public void verifyProductNameAndPriceTest() {
        HeaderComponent header = new HeaderComponent(getDriver());
        ProductListPage plp = header.clickProducts();
        ProdutDetailsPage pdp = plp.clickViewProduct();
        Assert.assertTrue(pdp.isProductNameDisplayed());
        Assert.assertTrue(pdp.isProductPriceDisplayed());
    }
}
