package smg.tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    final int TIME_OUT_IN_SECONDS = 20;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    protected void setUp(String browser) {
        driver = launchBrowser(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));
    }

    @AfterMethod(alwaysRun = true)
    protected void captureScreenShot(ITestResult testResult) {
        if (!testResult.isSuccess()) {
            String methodName = testResult.getMethod().getMethodName();
            long currentTime = System.currentTimeMillis();
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File(String.format("./target/%s-screenshot-%s.png", methodName, currentTime));
            try {
                FileUtils.copyFile(SrcFile, DestFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebDriver launchBrowser(String name){
        switch (name){
            case "firefox": {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                //firefoxOptions.setHeadless(true);
                return new FirefoxDriver(firefoxOptions);
            }
            case "safari":{
                return new SafariDriver();
            }
            default: {
                ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.setHeadless(true);
                return new ChromeDriver(chromeOptions);
            }
        }
    }

}
