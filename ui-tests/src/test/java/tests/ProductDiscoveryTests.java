package tests;

import base.BaseTest;
import base.TeardownAndTeardownTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProductPage;

public class ProductDiscoveryTests extends BaseTest {

    @BeforeMethod
    public void signInBeforeTest() {
        TeardownAndTeardownTest.signIn(driver);
    }

    @AfterMethod
    public void signOutAfterTest() {
        TeardownAndTeardownTest.signOut(driver);
    }

    @Test(description = "Verify that product information is correct: category, name, description, price, and availability")
    public void verifyProductInformationIsCorrect() {
        ProductPage productPage = new ProductPage(driver);
        productPage.open();

        Assert.assertTrue(productPage.isProductGridVisible(),
                "Product grid should be visible on the home page");

        // --- Find the target product card by name ---
        WebElement targetCard = null;
        for (WebElement card : productPage.getProductCards()) {
            if (productPage.getProductName(card).contains("Organic Hass Avocados")) {
                targetCard = card;
                break;
            }
        }

        Assert.assertNotNull(targetCard,
                "Product 'Organic Hass Avocados' should be present in the product grid");

        // --- Validate category ---
        String category = productPage.getProductCategory(targetCard);
        Assert.assertEquals(category, "FRESH PRODUCE",
                "Product category should be 'FRESH PRODUCE', but was: '" + category + "'");

        // --- Validate name ---
        String name = productPage.getProductName(targetCard);
        Assert.assertEquals(name, "Organic Hass Avocados",
                "Product name should be 'Organic Hass Avocados', but was: '" + name + "'");

        // --- Validate description ---
        String description = productPage.getProductDescription(targetCard);
        Assert.assertEquals(description,
                "Tray of 6 ripe organic Hass avocados, grown in the Bay of Plenty.",
                "Product description should match expected value");

        // --- Validate price and unit ---
        String price = productPage.getProductPrice(targetCard);
        String unit = productPage.getProductUnit(targetCard);
        Assert.assertEquals(price, "$9.50",
                "Product price should be '$9.50', but was: '" + price + "'");
        Assert.assertEquals(unit, "/ 6-pack",
                "Product unit should be '/ 6-pack', but was: '" + unit + "'");

        // --- Validate stock/availability ---
        String stock = productPage.getProductStock(targetCard);
        Assert.assertEquals(stock, "60 in stock",
                "Product stock should be '60 in stock', but was: '" + stock + "'");
    }
}
