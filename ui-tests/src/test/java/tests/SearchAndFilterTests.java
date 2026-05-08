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
        page.verifyAllDescriptionsEqual("milk");

        page.clearSearch();

        page.searchByKeyword("bread");
        page.verifyAllDescriptionsEqual("bread");
    }

    @Test(description = "SF-003 — Verify that search and category filtering work together (category:frozen+keyword:Cream, category:frozen+keyword:meat)")
    public void searchAndFilterTogether() {
        SearchAndFilterPage page = new SearchAndFilterPage(driver);
        page.open();

        page.selectCategory("Frozen");
        page.searchByKeyword("Cream");
 
        page.verifyAllCategoriesEqual("Frozen");
        page.verifyAllDescriptionsEqual("bread");

        page.clearSearch();
        page.selectCategory("All categories");

        page.selectCategory("Frozen");
        page.searchByKeyword("meat");
        page.verifyNoProductsMessageVisible();    
    }

    @Test(description = "SF-004 — Verify that searching with no matching results shows empty state (noodles)")
    public void noResultsEmptyState() {
        SearchAndFilterPage page = new SearchAndFilterPage(driver);
        page.open();

        page.searchByKeyword("noodles");

        page.verifyNoProductsMessageVisible();
    }
}
