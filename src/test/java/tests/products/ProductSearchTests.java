package tests.products;

import base.BaseTest;
import components.HeaderComponent;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductListPage;
import pages.ProdutDetailsPage;
import pages.SearchResultsPage;

import java.util.HashMap;

public class ProductSearchTests extends BaseTest {


    @Test (groups="smoke", dataProviderClass = dataproviders.TestDataProvider.class, dataProvider = "searchData")
    public void searchForProductTest(HashMap<String, String> input) {
        HeaderComponent header = new HeaderComponent(getDriver());
        ProductListPage plp = header.clickProducts();
        SearchResultsPage searchResults = plp.searchProduct(input.get("product"));
        Assert.assertEquals(searchResults.isSearchedProductNameDisplayed(),input.get("product"));
        ProdutDetailsPage pdp = searchResults.clickViewProduct();
        Assert.assertEquals(pdp.getProductNameText(),input.get("product"));
    }

}
