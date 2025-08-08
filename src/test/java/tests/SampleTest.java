package tests;

import io.qameta.allure.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class SampleTest {

    private WebDriver driver;

    @BeforeClass
    @Step("Setup Chrome driver")
    public void setup() {
        // You can set your chromedriver path here if needed
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(description = "Open Ahmed Hasan personal website and verify title")
    @Description("Verify the home page loads and title contains 'Ahmed Hasan'")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Open URL: https://www.ahmed-hasan.wuaze.com/")
    public void openHomePage() {
        driver.get("https://www.ahmed-hasan.wuaze.com/");
        Allure.step("Page opened");
        String title = driver.getTitle();
        Allure.step("Page title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("ahmed hasan"), "Title should contain 'Ahmed Hasan'");
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
