package pages;

import base.BasePage;
import utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage extends BasePage {

    private final By form = By.cssSelector("[data-testid='register-form']");
    private final By nameInput = By.cssSelector("[data-testid='register-name']");
    private final By emailInput = By.cssSelector("[data-testid='register-email']");
    private final By passwordInput = By.cssSelector("[data-testid='register-password']");
    private final By submitButton = By.cssSelector("[data-testid='register-submit']");
    private final By errorMessage = By.cssSelector("[data-testid='register-error']");
    private final By loginLink = By.partialLinkText("Log in");

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(ConfigReader.getBaseUrl() + "/register");
        waitForRegisterForm();
    }

    private void waitForRegisterForm() {
        waitForUrlContains("/register");
        findElement(form);
    }

    public void register(String name, String email, String password) {
        waitForRegisterForm();
        type(nameInput, name);
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);

        try {
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(d -> !d.getCurrentUrl().contains("/register"));
        } catch (TimeoutException e) {
            throw new RuntimeException("Navigation failed after registration. URL is still: " + driver.getCurrentUrl() +
                    ", Error visible: " + getErrorMessage(), e);
        }
    }

    public void registerWithoutName(String email, String password) {
        waitForRegisterForm();
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
    }

    public void registerWithoutEmail(String name, String password) {
        waitForRegisterForm();
        type(nameInput, name);
        type(passwordInput, password);
        click(submitButton);
    }

    public void registerWithoutPassword(String name, String email) {
        waitForRegisterForm();
        type(nameInput, name);
        type(emailInput, email);
        click(submitButton);
    }

    public void submitForm(String name, String email, String password) {
        waitForRegisterForm();
        type(nameInput, name);
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
    }

    public String getErrorMessage() {
        if (isElementVisible(errorMessage)) {
            return getText(errorMessage);
        }
        return "";
    }

    public boolean isErrorVisible() {
        return isElementVisible(errorMessage);
    }

    public boolean isFormVisible() {
        return isElementPresent(form);
    }

    public void clickLoginLink() {
        click(loginLink);
    }

    public void goToSignIn() {
        clickLoginLink();
    }

    public By errorElement() {
        return errorMessage;
    }
}
