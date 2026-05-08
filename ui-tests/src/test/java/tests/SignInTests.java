package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NavBar;
import pages.SignInPage;
import pages.SignUpPage;

public class SignInTests extends BaseTest {

    @Test(description = "Verify that a user can successfully sign in with valid credentials")
    public void signInWithValidCredentialsNavigatesAwayFromLogin() {
        String email = "signintest" + System.currentTimeMillis() + "@example.com";
        String password = "password123";

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.open();
        signUpPage.register("Sign In Test User", email, password);

        driver.get(System.getProperty("base.url", "http://localhost:5173") + "/login");

        SignInPage signInPage = new SignInPage(driver);
        Assert.assertTrue(signInPage.isFormVisible(), "Login form should be visible");

        signInPage.login(email, password);

        NavBar navBar = new NavBar(driver);
        Assert.assertTrue(navBar.isLoggedIn(), "User should be logged in after successful login");
    }

    @Test(description = "Verify that signing in with an unregistered email shows an error")
    public void signInWithUnregisteredEmailShowsError() {
        SignInPage signInPage = new SignInPage(driver);
        signInPage.open();

        signInPage.login("notregistered" + System.currentTimeMillis() + "@example.com", "password123");

        String error = signInPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(), "Error message should be displayed for unregistered email");
        Assert.assertTrue(
                error.toLowerCase().contains("invalid") ||
                error.toLowerCase().contains("credential") ||
                error.toLowerCase().contains("email") ||
                error.toLowerCase().contains("exist") ||
                error.toLowerCase().contains("incorrect"),
                "Error message should indicate invalid credentials: " + error);
    }

    @Test(description = "Verify that signing in with wrong password shows an error")
    public void signInWithWrongPasswordShowsError() {
        String email = "wrongpw" + System.currentTimeMillis() + "@example.com";
        String password = "password123";

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.open();
        signUpPage.register("Wrong Password Test", email, password);

        driver.get(System.getProperty("base.url", "http://localhost:5173") + "/login");

        SignInPage signInPage = new SignInPage(driver);
        signInPage.login(email, "wrongpassword");

        String error = signInPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(), "Error message should be displayed for wrong password");
        Assert.assertTrue(
                error.toLowerCase().contains("invalid") ||
                error.toLowerCase().contains("credential") ||
                error.toLowerCase().contains("password") ||
                error.toLowerCase().contains("incorrect"),
                "Error message should indicate invalid credentials: " + error);
    }

}
