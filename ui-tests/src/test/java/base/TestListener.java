package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.DriverFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

    private static final String SCREENSHOT_DIR = "target/screenshots";

    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshot(result.getName());
    }

    private void takeScreenshot(String testName) {
        try {
            var driver = DriverFactory.getDriver();
            if (driver instanceof TakesScreenshot ts) {
                File screenshotDir = new File(SCREENSHOT_DIR);
                screenshotDir.mkdirs();
                File destFile = new File(screenshotDir, testName + ".png");
                org.openqa.selenium.io.FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), destFile);
                System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}
