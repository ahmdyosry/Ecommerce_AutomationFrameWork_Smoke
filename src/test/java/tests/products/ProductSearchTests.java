package tests.products;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductListPage;
import pages.ProductDetailsPage;
import pages.SearchResultsPage;

import java.util.HashMap;

public class ProductSearchTests extends BaseTest {


    @Test(groups = "smoke", dataProviderClass = dataproviders.TestDataProvider.class, dataProvider = "searchData")
    public void searchForProductTest(HashMap<String, String> input) {
        SearchResultsPage searchResults = new ProductListPage(getDriver()).open().searchProduct(input.get("product"));
        Assert.assertEquals(searchResults.getSearchedProductNameText(), input.get("product"));
        ProductDetailsPage pdp = searchResults.clickViewProduct();
        Assert.assertEquals(pdp.getProductNameText(), input.get("product"));
    }

}
