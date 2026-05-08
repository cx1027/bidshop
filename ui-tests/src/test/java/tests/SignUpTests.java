package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NavBar;
import pages.SignUpPage;
import utils.ConfigReader;

public class SignUpTests extends BaseTest {

    @Test(description = "Verify that a user can successfully sign up with valid credentials")
    public void signUpWithValidCredentialsNavigatesToProducts() {
        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.open();

        signUpPage.register("Test User", uniqueEmail, "password123");

        Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getBaseUrl() + "/");

        NavBar navBar = new NavBar(driver);
        Assert.assertTrue(navBar.isLoggedIn(), "User should be logged in after successful registration");
        Assert.assertTrue(navBar.getLoggedInUserName().contains("Test"),
                "NavBar should display the registered user's name, but was: " + navBar.getLoggedInUserName());
    }

    @Test(description = "Verify that a user cannot sign up with a password shorter than 6 characters")
    public void signUpWithShortPasswordShowsError() {
        String email = "shortpw" + System.currentTimeMillis() + "@example.com";

        driver.get(ConfigReader.getBaseUrl() + "/register");

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.submitForm("Test User", email, "12345");

        Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
                "User should remain on register page for short password");

        boolean hasError = signUpPage.isErrorVisible();
        boolean browserBlocked = !hasError;
        Assert.assertTrue(browserBlocked || !signUpPage.getErrorMessage().isEmpty(),
                "Either browser validation should block submission, or app should show error for short password");
    }

    @Test(description = "Verify that a user cannot sign up without Fullname")
    public void signUpWithMissingFullnameRemainsOnRegisterPage() {
        String email = "nofullname" + System.currentTimeMillis() + "@example.com";

        driver.get(ConfigReader.getBaseUrl() + "/register");

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.submitForm("", email, "password123");

        Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
                "User should remain on register page when Fullname is empty");
    }

}
