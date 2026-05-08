package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SignUpPage;
import utils.ConfigReader;

public class EmailValidationTests extends BaseTest {

    @Test(description = "Verify that browser blocks registration with invalid email format (missing @)")
    public void emailValidationMissingAtSymbol() {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.open();

        signUpPage.submitForm("Test User", "invalidemail.com", "password123");

        Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getBaseUrl() + "/register");
    }

    @Test(description = "Verify that browser blocks registration with valid email format (missing username)")
    public void emailValidationMissingUsername() {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.open();

        signUpPage.submitForm("Test User", "@example.com", "password123");

        Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getBaseUrl() + "/register");
    }

    @Test(description = "Verify that registration succeeds with valid email format")
    public void emailValidationValidEmails() {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.open();

        String uniqueEmail = "valid" + System.currentTimeMillis() + "@example.com";
        signUpPage.register("Test User", uniqueEmail, "password123");

        Assert.assertEquals(driver.getCurrentUrl(), ConfigReader.getBaseUrl() + "/");
    }
}
