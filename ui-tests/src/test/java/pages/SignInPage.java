package pages;

import base.BasePage;
import utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {

    private final By form = By.cssSelector("[data-testid='login-form']");
    private final By emailInput = By.cssSelector("[data-testid='login-email']");
    private final By passwordInput = By.cssSelector("[data-testid='login-password']");
    private final By submitButton = By.cssSelector("[data-testid='login-submit']");
    private final By errorMessage = By.cssSelector("[data-testid='login-error']");
    private final By registerLink = By.partialLinkText("Register");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(ConfigReader.getBaseUrl() + "/login");
        waitForLoginForm();
    }

    private void waitForLoginForm() {
        waitForUrlContains("/login");
        findElement(form);
    }

    public void login(String email, String password) {
        waitForLoginForm();
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
        waitForUrlContains("/");
    }

    public void loginWithoutEmail(String password) {
        waitForLoginForm();
        type(passwordInput, password);
        click(submitButton);
    }

    public void loginWithoutPassword(String email) {
        waitForLoginForm();
        type(emailInput, email);
        click(submitButton);
    }

    public String getErrorMessage() {
        if (isElementVisible(errorMessage)) {
            return getText(errorMessage);
        }
        return "";
    }

    public boolean isFormVisible() {
        return isElementPresent(form);
    }

    public void clickRegisterLink() {
        click(registerLink);
    }

    public void goToSignUp() {
        clickRegisterLink();
    }
}
