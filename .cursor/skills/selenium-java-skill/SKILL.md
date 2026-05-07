---
name: selenium-java
description: Expert-level guidance for browser automation and enterprise web testing using Selenium WebDriver with Java, emphasizing robustness, scalability, and clean architecture.
---

# Selenium Browser Automation

You are an expert in Selenium WebDriver using Java, specializing in building scalable, maintainable, and
high-performance automated test frameworks for modern web applications.

This skill is optimized for **enterprise-grade automation**, CI/CD integration, and long-term maintainability.

---

## Core Expertise

- Selenium WebDriver internals and Java bindings
- Browser drivers: Firefox
- Element location strategies (CSS, XPath, accessibility-first selectors)
- Explicit waits and fluent waits for dynamic web apps
- Page Object Model (POM) and Page Factory patterns
- Test orchestration with TestNG and JUnit 5
- Maven & Gradle-based automation frameworks

---

## Guiding Principles

- Tests are **code**, not scripts - design them like production software
- Favor **explicit waits** and domain - specific abstractions
- Enforce **single responsibility** at page and test levels
- Keep tests **deterministic and isolated**
- Optimize for **parallelism first**, not as an afterthought
- Eliminate flakiness through synchronization, not retries

---

## Recommended Project Structure

```text
src
 └── test
     ├── java
     │   ├── base
     │   │   ├── BaseTest.java
     │   │   └── BasePage.java
     │   ├── pages
     │   │   ├── LoginPage.java
     │   │   └── DashboardPage.java
     │   ├── tests
     │   │   ├── LoginTests.java
     │   │   └── DashboardTests.java
     │   └── utils
     │       ├── DriverFactory.java
     │       ├── WaitUtils.java
     │       ├── WaitUtilsKeywords.java
     │       └── ConfigReader.java
     └── resources
         ├── config.properties
         └── testng.xml
 ```

This structure scales cleanly from 10 tests to 10,000 tests without entropy.

---

## WebDriver Setup

### Driver Factory Pattern

```java 
package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        WebDriverManager.chromedriver().setup();
        driver.set(new ChromeDriver(options));
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.remove();
    }
}
```

ThreadLocal enables true parallel execution, not simulated concurrency.

---

## Base Test Setup

```java
package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver(true);
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
```
---

## Page Object Model

### Base Page

The BasePage delegates all element interactions to `WaitUtilsKeywords`, keeping the page layer focused on domain logic:

```java
package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtilsKeywords;

public class BasePage {

    protected WebDriver driver;
    protected WaitUtilsKeywords kw;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.kw = new WaitUtilsKeywords(driver, 10);
    }

    // click — waits until element is clickable
    protected void click(By locator) {
        kw.clickByLocator(locator);
    }

    // type — waits until element is visible, then clears and sends keys
    protected void type(By locator, String text) {
        kw.typeByLocator(locator, text);
    }

    // getText — waits until element is visible
    protected String getText(By locator) {
        return kw.getTextByLocator(locator);
    }

    // getAttribute — waits until element is visible
    protected String getAttribute(By locator, String attribute) {
        return kw.getAttributeByLocator(locator, attribute);
    }

    // isElementPresent — checks existence within timeout
    protected boolean isElementPresent(By locator) {
        return kw.isElementPresent(locator);
    }
}
```

> **Note:** `WaitUtilsKeywords` exposes both `By`-based methods (for reuse in page objects) and `String`-based convenience methods (for direct test use). See the `### WaitUtilsKeywords` section for the full API.
---

### Page Object Implementation

```java
package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By usernameInput = By.id("username");
    private By passwordInput = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.className("error-message");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

}
```
---

### Element Location Strategy

Priority order:
1. id
2. name
3. data-testid
4. CSS selectors
5. XPath (only when relationships matter)

```java
    By.cssSelector("[data-testid='submit-button']");
    By.xpath("//label[text()='Email']/following-sibling::input");
```
---

## Wait Strategy

### Explicit & Fluent Waits

```java
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal")));
```

```java
    Wait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(15))
            .pollingEvery(Duration.ofMillis(500))
            .ignoring(NoSuchElementException.class);
```

Hard sleeps are automation debt—never amortize them.

### WaitUtilsKeywords

Every Selenium keyword should be wrapped in a wait to eliminate flakiness at the source:

```java
package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtilsKeywords {

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtilsKeywords(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    // findElement by id — waits until element is displayed
    public WebElement findElementById(String id) {
        return wait.until(driver -> {
            WebElement element = driver.findElement(By.id(id));
            return element.isDisplayed() ? element : null;
        });
    }

    // findElement by xpath — waits until element is displayed
    public WebElement findElementByXpath(String xpath) {
        return wait.until(driver -> {
            WebElement element = driver.findElement(By.xpath(xpath));
            return element.isDisplayed() ? element : null;
        });
    }

    // click — waits until element is clickable
    public void clickById(String id) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(By.id(id))
        );
        element.click();
    }

    // type — waits until element is visible, then clears and sends keys
    public void typeById(String id, String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        );
        element.clear();
        element.sendKeys(text);
    }

    // getText — waits until element is visible
    public String getTextById(String id) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        );
        return element.getText();
    }

    // isElementPresent — returns true if element is present within timeout
    public boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // findVisibleElements — waits until all matching elements are visible
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

    public String getAttributeByLocator(By locator, String attribute) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getAttribute(attribute);
    }
}
```

Apply this pattern consistently: every interaction (click, type, getText, etc.) must be preceded by the appropriate wait condition. No hard sleeps, no retry loops — synchronization is the only durable fix for flakiness.

---

## Test Writing (TestNG)

```java
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    @Test
    public void loginWithValidCredentialsNavigatesToDashboard() {
        driver.get("https://example.com/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("valid_user", "valid_pass");

        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

    @Test
    public void loginWithInvalidPasswordShowsError() {
        driver.get("https://example.com/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("valid_user", "wrong_pass");

        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Invalid credentials"));
    }

}
```
---

## Handling Complex Web Elements

### Dropdowns

```java
    // Select by visible text — waits for element to be present first
    kw.isElementPresent(By.id("country")); // ensure dropdown is in DOM
    Select select = new Select(kw.findElementById("country"));
    select.selectByVisibleText("Germany");

    // Select by value
    select.selectByValue("de");

    // Select by index
    select.selectByIndex(2);

    // Get all options
    List<WebElement> options = select.getOptions();
    for (WebElement option : options) {
        System.out.println(option.getText());
    }

    // Verify selected option
    String selected = select.getFirstSelectedOption().getText();
    Assert.assertEquals(selected, "Germany");
```

### Alerts

```java
    import org.openqa.selenium.Alert;

    // Accept alert — wait for it to appear
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String alertText = alert.getText();
    alert.accept();

    // Dismiss alert
    Alert dismissAlert = wait.until(ExpectedConditions.alertIsPresent());
    dismissAlert.dismiss();

    // Send keys to prompt
    Alert promptAlert = wait.until(ExpectedConditions.alertIsPresent());
    promptAlert.sendKeys("input value");
    promptAlert.accept();
```

### Frames

```java
    // Switch to frame by name or id — wait for presence
    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frameName"));

    // Switch to frame by index
    driver.switchTo().frame(0);

    // Switch to iframe by WebElement
    WebElement iframe = kw.findElementByXpath("//iframe[@id='myIframe']");
    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));

    // Return to main document
    driver.switchTo().defaultContent();

    // Switch back to parent frame (nested frames)
    driver.switchTo().parentFrame();
```

### Multiple Windows

```java
    // Store main window
    String mainWindow = driver.getWindowHandle();

    // Trigger new window (e.g., click a link that opens a new tab)
    kw.clickById("open-new-window");

    // Wait for new window and switch to it
    wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    for (String windowHandle : driver.getWindowHandles()) {
        if (!windowHandle.equals(mainWindow)) {
            driver.switchTo().window(windowHandle);
            break;
        }
    }

    // Now on new window — do something
    kw.getTextById("new-window-heading");

    // Close new window and switch back
    driver.close();
    driver.switchTo().window(mainWindow);
```
---

## Parallel Execution

### TestNG Configuration

```java
    <suite name="Automation Suite" parallel="tests" thread-count="5">
        <test name="Login Tests">
            <classes>
                <class name="tests.LoginTests"/>
            </classes>
        </test>
    </suite>
```

Designed for seamless scaling into Selenium Grid or cloud providers.

---

### Key Dependencies (Maven)

```java
    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.x.x</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
        </dependency>
    </dependencies>
```
---

## Reliability & Observability

- Screenshot capture on failure
- Structured logging per test thread
- RetryAnalyzer only for environmental flakiness
- CI-friendly reports (Allure / Extent Reports)

---

## Debugging Playbook

- Run non-headless locally for DOM inspection
- Dump page source on failures
- Capture browser logs when supported
- Treat flaky tests as design bugs, not nuisances