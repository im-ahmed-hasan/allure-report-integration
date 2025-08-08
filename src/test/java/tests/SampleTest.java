package tests;

import io.qameta.allure.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class SampleTest {

    /*
     * Author: Ahmed Hasan
     * © Ahmed Hasan 2025. All rights reserved.
     *
     * This example demonstrates Selenium tests with Allure Reporting
     * including pass, skip, and fail scenarios with screenshot attachments.
     */

    private WebDriver driver;

    @BeforeClass
    @Step("Setup Chrome driver")
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(description = "Passing test - open Ahmed Hasan personal website and verify")
    @Description("Verify the home page loads and title contains 'Ahmed Hasan'")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Open URL: https://www.ahmed-hasan.wuaze.com/")
    public void testPass() {
        driver.get("https://www.ahmed-hasan.wuaze.com/");
        Allure.step("Page opened");
        String title = driver.getTitle();
        Allure.step("Page title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("ahmed hasan"), "Title should contain 'Ahmed Hasan'");
    }

    @Test(description = "Skipped test #1")
    @Severity(SeverityLevel.MINOR)
    public void testSkip1() {
        throw new SkipException("Skipping this test intentionally");
    }

    @Test(description = "Skipped test #2")
    @Severity(SeverityLevel.MINOR)
    public void testSkip2() {
        throw new SkipException("Skipping this test intentionally as well");
    }

    @Test(description = "Failing test with screenshot on failure")
    @Severity(SeverityLevel.BLOCKER)
    public void testFail() {
        driver.get("https://www.ahmed-hasan.wuaze.com/");
        // Intentionally fail by asserting wrong title
        Assert.assertTrue(driver.getTitle().contains("NonExistentTitle"), "This test is designed to fail");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Attach screenshot on failure to Allure report
        if (ITestResult.FAILURE == result.getStatus()) {
            Allure.addAttachment("Screenshot on Failure",
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }
    }

    @AfterClass
    @Step("Quit driver")
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}
