package tests.products;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;
import retry.RetryAnalyzer;

public class ProductListingTests extends BaseTest {

    @Test(groups = "smoke", retryAnalyzer = RetryAnalyzer.class)
    public void categoryNavigationTest() {
        HomePage home = new HomePage(getDriver());
        CategoryPage category = home.navigateToMenTshirtsCategory();
        Assert.assertEquals(category.getBreadcrumbText(), "Men > Tshirts");
        Assert.assertTrue(category.getCategoryTitle().equalsIgnoreCase("Men - Tshirts Products"));
        Assert.assertTrue(category.isProductsDisplayed());
    }


}
