package pages;

import base.BasePage;
import org.testng.Assert;
import utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SearchAndFilterPage extends BasePage {

    private final By productGrid = By.cssSelector("[data-testid='product-grid']");
    private final By filterSearch = By.cssSelector("[data-testid='filter-search']");
    private final By filterCategory = By.cssSelector("[data-testid='filter-category']");
    private final By filterSummary = By.cssSelector("[data-testid='filter-summary']");
    private final By emptyState = By.cssSelector("[data-testid='empty-state']");

    public SearchAndFilterPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(ConfigReader.getBaseUrl());
        findElement(productGrid);
    }

    public List<WebElement> getProductCards() {
        return findVisibleElements(By.cssSelector("[data-testid^='product-card-']"));
    }

    public void selectCategory(String categoryLabel) {
        WebElement dropdown = findElement(filterCategory);
        Select select = new Select(dropdown);
        select.selectByVisibleText(categoryLabel);
    }

    public void searchByKeyword(String keyword) {
        WebElement searchInput = findElement(filterSearch);
        searchInput.clear();
        searchInput.sendKeys(keyword);
    }

    public String getFilterSummaryText() {
        return getText(filterSummary);
    }

    public boolean isEmptyStateVisible() {
        return isElementVisible(emptyState);
    }

    public boolean isProductGridVisible() {
        return isElementPresent(productGrid);
    }

    public String getProductName(WebElement card) {
        WebElement el = card.findElement(By.cssSelector("[data-testid^='product-name-']"));
        return el != null ? el.getText().trim() : "";
    }

    public String getProductCategory(WebElement card) {
        WebElement el = card.findElement(By.cssSelector("[data-testid^='product-category-']"));
        return el != null ? el.getText().trim() : "";
    }

    public List<String> getDisplayedProductNames() {
        return getProductCards().stream()
                .map(this::getProductName)
                .toList();
    }

    public List<String> getDisplayedProductCategories() {
        return getProductCards().stream()
                .map(this::getProductCategory)
                .toList();
    }

    public void clearSearch() {
        WebElement searchInput = findElement(filterSearch);
        searchInput.clear();
    }

    public void clearCategory() {
        WebElement dropdown = findElement(filterCategory);
        Select select = new Select(dropdown);
        select.selectByIndex(0);
    }

    public void verifyAllCategoriesEqual(String expectedCategory) {
        List<String> categories = getDisplayedProductCategories();
        for (String category : categories) {
            Assert.assertEquals(category, expectedCategory,
                    "Product category '" + category + "' does not match expected category '" + expectedCategory + "'");
        }
    }
}
