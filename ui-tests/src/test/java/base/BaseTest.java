package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utils.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    @Parameters("headless")
    public void setUp(@org.testng.annotations.Optional("false") String headless) {
        boolean isHeadless = Boolean.parseBoolean(headless);
        DriverFactory.initDriver(isHeadless);
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
