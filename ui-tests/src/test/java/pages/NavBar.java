package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavBar extends BasePage {

    private final By navHome = By.cssSelector("[data-testid='nav-home']");
    private final By navProducts = By.cssSelector("[data-testid='nav-products']");
    private final By navCart = By.cssSelector("[data-testid='nav-cart']");
    private final By navLogin = By.cssSelector("[data-testid='nav-login']");
    private final By navRegister = By.cssSelector("[data-testid='nav-register']");
    private final By navLogout = By.cssSelector("[data-testid='nav-logout']");
    private final By navUserName = By.cssSelector("[data-testid='nav-user-name']");

    public NavBar(WebDriver driver) {
        super(driver);
    }

    public void goToHome() {
        click(navHome);
    }

    public void goToProducts() {
        click(navProducts);
    }

    public void goToCart() {
        click(navCart);
    }

    public void goToLogin() {
        click(navLogin);
    }

    public void goToRegister() {
        click(navRegister);
    }

    public void logout() {
        if (isElementPresent(navLogout)) {
            click(navLogout);
        }
    }

    public boolean isLoggedIn() {
        return isElementPresent(navLogout);
    }

    public boolean isLoggedOut() {
        return isElementPresent(navLogin);
    }

    public String getLoggedInUserName() {
        if (isElementPresent(navUserName)) {
            return getText(navUserName);
        }
        return "";
    }
}
