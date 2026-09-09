package tests.products;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductListPage;
import pages.ProdutDetailsPage;

public class ProductDetailsTests extends BaseTest {

    @Test(groups = "smoke")
    public void verifyProductNameAndPriceTest() {
        ProductListPage plp = new ProductListPage(getDriver()).open();
        ProdutDetailsPage pdp = plp.clickViewProduct();
        Assert.assertTrue(pdp.isProductNameDisplayed());
        Assert.assertTrue(pdp.isProductPriceDisplayed());
    }
}
