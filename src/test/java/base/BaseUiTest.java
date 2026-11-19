package base;

import config.TestConfig;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ui.DriverFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BaseUiTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(TestConfig.uiBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws Exception {
        try {
            if (driver != null && result.getStatus() == ITestResult.FAILURE) {
                Path screenshotDirectory = Path.of("target", "screenshots");
                Files.createDirectories(screenshotDirectory);

                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(
                        screenshot.toPath(),
                        screenshotDirectory.resolve(result.getName() + ".png"),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
