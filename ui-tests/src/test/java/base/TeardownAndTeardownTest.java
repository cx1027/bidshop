package base;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NavBar;
import pages.SignInPage;
import utils.ConfigReader;

public class TeardownAndTeardownTest extends BaseTest {

    private static final String EMAIL = "producttest@example.com";
    private static final String PASSWORD = "password123";

    public static void signIn(WebDriver driver) {
        driver.get(ConfigReader.getBaseUrl() + "/login");
        SignInPage signInPage = new SignInPage(driver);
        Assert.assertTrue(signInPage.isFormVisible(), "Login form should be visible");
        signInPage.login(EMAIL, PASSWORD);
        Assert.assertTrue(new NavBar(driver).isLoggedIn(), "User should be logged in after successful login");
    }

    public static void signOut(WebDriver driver) {
        NavBar navBar = new NavBar(driver);
        Assert.assertTrue(navBar.isLoggedIn(), "User should be logged in before sign out");
        navBar.logout();
        Assert.assertFalse(navBar.isLoggedIn(), "User should be logged out after sign out");
    }

    @Test(description = "Verify that a user can successfully sign in with valid credentials")
    public void signInWithValidCredentialsNavigatesAwayFromLogin() {
        signIn(driver);
    }

    @Test(description = "Verify that a logged-in user can successfully sign out")
    public void signOutSuccessfullyLogsUserOut() {
        signOut(driver);
    }
}
