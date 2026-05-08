package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtilsKeywords {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtilsKeywords(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public WaitUtilsKeywords(WebDriver driver) {
        this(driver, 10);
    }

    // findElement by id
    public WebElement findElementById(String id) {
        return wait.until(driver -> {
            WebElement element = driver.findElement(By.id(id));
            return element.isDisplayed() ? element : null;
        });
    }

    // findElement by xpath
    public WebElement findElementByXpath(String xpath) {
        return wait.until(driver -> {
            WebElement element = driver.findElement(By.xpath(xpath));
            return element.isDisplayed() ? element : null;
        });
    }

    // click by id
    public void clickById(String id) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(By.id(id))
        );
        element.click();
    }

    // click by xpath
    public void clickByXpath(String xpath) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(xpath))
        );
        element.click();
    }

    // type by id
    public void typeById(String id, String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        );
        element.clear();
        element.sendKeys(text);
    }

    // type by xpath
    public void typeByXpath(String xpath, String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
        );
        element.clear();
        element.sendKeys(text);
    }

    // getText by id
    public String getTextById(String id) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        ).getText();
    }

    // getText by xpath
    public String getTextByXpath(String xpath) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
        ).getText();
    }

    // getAttribute by id
    public String getAttributeById(String id, String attribute) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        ).getAttribute(attribute);
    }

    // getAttribute by locator
    public String getAttributeByLocator(By locator, String attribute) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getAttribute(attribute);
    }

    // isElementPresent
    public boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // isElementVisible
    public boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // findVisibleElements
    public List<WebElement> findVisibleElements(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)
        );
    }

    // ── By-based methods (for use in Page Objects) ──────────────────────

    public void clickByLocator(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void typeByLocator(By locator, String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        element.clear();
        element.sendKeys(text);
    }

    public String getTextByLocator(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getText();
    }

    public WebElement findElementByLocator(By locator) {
        return wait.until(driver -> {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed() ? element : null;
        });
    }

    public void scrollIntoView(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void waitForUrlContains(String text) {
        wait.until(ExpectedConditions.urlContains(text));
    }

    public void waitForUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }

    public void acceptAlertIfPresent() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (TimeoutException e) {
            // No alert present, ignore
        }
    }
}
