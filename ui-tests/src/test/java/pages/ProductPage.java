package pages;

import base.BasePage;
import utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage extends BasePage {

    private final By productGrid = By.cssSelector("[data-testid='product-grid']");
    private final By filterSearch = By.cssSelector("[data-testid='filter-search']");
    private final By filterCategory = By.cssSelector("[data-testid='filter-category']");
    private final By filterSummary = By.cssSelector("[data-testid='filter-summary']");
    private final By emptyState = By.cssSelector("[data-testid='empty-state']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(ConfigReader.getBaseUrl());
        findElement(productGrid);
    }

    public List<WebElement> getProductCards() {
        return findVisibleElements(By.cssSelector("[data-testid^='product-card-']"));
    }

    public String getProductName(WebElement card) {
        WebElement el = card.findElement(By.cssSelector("[data-testid^='product-name-']"));
        return el != null ? el.getText().trim() : "";
    }

    public String getProductPrice(WebElement card) {
        WebElement el = card.findElement(By.cssSelector("[data-testid^='product-price-']"));
        return el != null ? el.getText().trim() : "";
    }

    public String getProductUnit(WebElement card) {
        WebElement el = card.findElement(By.cssSelector(".product-card__unit"));
        return el != null ? el.getText().trim() : "";
    }

    public String getProductDescription(WebElement card) {
        WebElement el = card.findElement(By.cssSelector(".product-card__desc"));
        return el != null ? el.getText().trim() : "";
    }

    public boolean hasProductImage(WebElement card) {
        List<WebElement> images = card.findElements(By.cssSelector("img.product-card__image"));
        return !images.isEmpty() && images.get(0).getAttribute("src") != null
                && !images.get(0).getAttribute("src").isEmpty();
    }

    public String getProductCategory(WebElement card) {
        WebElement el = card.findElement(By.cssSelector("[data-testid^='product-category-']"));
        return el != null ? el.getText().trim() : "";
    }

    public String getProductStock(WebElement card) {
        WebElement el = card.findElement(By.cssSelector("[data-testid^='product-stock-']"));
        return el != null ? el.getText().trim() : "";
    }

    public void search(String keyword) {
        findElement(filterSearch).clear();
        findElement(filterSearch).sendKeys(keyword);
    }

    public boolean isEmptyStateVisible() {
        return isElementVisible(emptyState);
    }

    public boolean isProductGridVisible() {
        return isElementPresent(productGrid);
    }
}
