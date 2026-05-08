package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void clickById(String id) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
    }

    protected void clickByXpath(String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        element.clear();
        element.sendKeys(text);
    }

    public void typeById(String id, String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        );
        element.clear();
        element.sendKeys(text);
    }

    protected void typeByXpath(String xpath, String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
        );
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getText();
    }

    public String getTextById(String id) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        ).getText();
    }

    protected String getTextByXpath(String xpath) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
        ).getText();
    }

    protected String getAttribute(By locator, String attribute) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getAttribute(attribute);
    }

    public String getAttributeById(String id, String attribute) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        ).getAttribute(attribute);
    }

    protected boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected WebElement findElement(By locator) {
        return wait.until(driver -> {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed() ? element : null;
        });
    }

    public WebElement findElementById(String id) {
        return wait.until(driver -> {
            WebElement element = driver.findElement(By.id(id));
            return element.isDisplayed() ? element : null;
        });
    }

    protected List<WebElement> findVisibleElements(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)
        );
    }

    protected void scrollIntoView(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void waitForUrlContains(String text) {
        wait.until(ExpectedConditions.urlContains(text));
    }

    protected void waitForUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }

    protected void acceptAlertIfPresent() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (TimeoutException e) {
            // No alert present, ignore
        }
    }
}
