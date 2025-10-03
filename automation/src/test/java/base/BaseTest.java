package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    public WebDriver driver;

    @BeforeMethod
    public void setup() {
    	
ChromeOptions option=new ChromeOptions();

Map<String, Object> prefs = new HashMap<>();
prefs.put("credentials_enable_service", false);
prefs.put("profile.password_manager_enabled", false);

option.setExperimentalOption("prefs", prefs);
        option.addArguments("--incognito");
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        if (ITestResult.FAILURE == result.getStatus()) {
            File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(scr.toPath(), new File("screenshot_" + result.getName() + ".png").toPath());
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
