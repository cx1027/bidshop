package tests;

import base.BaseTest;
import base.TeardownAndTeardownTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SearchAndFilterPage;

public class SearchAndFilterTests extends BaseTest {

    @BeforeMethod
    public void signInBeforeTest() {
        TeardownAndTeardownTest.signIn(driver);
    }

    @AfterMethod
    public void signOutAfterTest() {
        TeardownAndTeardownTest.signOut(driver);
    }

    @Test(description = "SF-001 — Verify that products can be filtered by category (Bakery, Dairy, All categories)")
    public void filterByCategory() {
        SearchAndFilterPage page = new SearchAndFilterPage(driver);
        page.open();

        page.selectCategory("Frozen");
        page.verifyAllCategoriesEqual("Frozen");

        page.selectCategory("Dairy");
        page.verifyAllCategoriesEqual("Dairy");

        page.selectCategory("All categories");
        page.verifyAllCategoriesEqual("All categories");
    }

    @Test(description = "SF-002 — Verify that keyword search returns relevant product results (Full Cream Milk, bread)")
    public void searchByKeyword() {
        SearchAndFilterPage page = new SearchAndFilterPage(driver);
        page.open();

        page.searchByKeyword("Full Cream Milk");
        Assert.assertTrue(page.isProductGridVisible(),
                "Product grid should be visible after keyword search");
        Assert.assertTrue(page.getDisplayedProductNames().stream()
                        .anyMatch(name -> name.toLowerCase().contains("milk")),
                "Search results should contain 'milk' products for keyword 'Full Cream Milk'");

        page.clearSearch();

        page.searchByKeyword("bread");
        Assert.assertTrue(page.isProductGridVisible(),
                "Product grid should be visible after keyword search");
        Assert.assertTrue(page.getDisplayedProductNames().stream()
                        .anyMatch(name -> name.toLowerCase().contains("bread")),
                "Search results should contain 'bread' products for keyword 'bread'");
    }

    @Test(description = "SF-003 — Verify that search and category filtering work together (category:frozen+keyword:Cream, category:frozen+keyword:meat)")
    public void searchAndFilterTogether() {
        SearchAndFilterPage page = new SearchAndFilterPage(driver);
        page.open();

        page.selectCategory("Frozen");
        page.searchByKeyword("Cream");

        Assert.assertTrue(page.isProductGridVisible(),
                "Product grid should be visible after combined search and filter");
        if (!page.getProductCards().isEmpty()) {
            Assert.assertTrue(page.getDisplayedProductCategories().stream()
                    .allMatch(cat -> cat.equalsIgnoreCase("Frozen")),
                    "All displayed products should belong to 'Frozen' category");
        }

        page.clearSearch();

        page.searchByKeyword("meat");
        Assert.assertTrue(page.isProductGridVisible(),
                "Product grid should be visible after updating search keyword");
        if (!page.getProductCards().isEmpty()) {
            Assert.assertTrue(page.getDisplayedProductCategories().stream()
                    .allMatch(cat -> cat.equalsIgnoreCase("Frozen")),
                    "All displayed products should still belong to 'Frozen' category after keyword update");
        }
    }

    @Test(description = "SF-004 — Verify that searching with no matching results shows empty state (noodles)")
    public void noResultsEmptyState() {
        SearchAndFilterPage page = new SearchAndFilterPage(driver);
        page.open();

        page.searchByKeyword("noodles");

        Assert.assertTrue(page.isEmptyStateVisible(),
                "Empty state should be visible when no products match the search keyword 'noodles'");
    }
}
