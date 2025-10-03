package tests;

import base.BaseTest;
import utils.CSVReaderUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PurchaseTest extends BaseTest {

    @Test
    public void testPurchaseFlow() throws Exception {
        // Read credentials from CSV
        String[] creds = CSVReaderUtil.getCredentials();
        String username = creds[0];
        String password = creds[1];
        
        

        driver.get("https://www.saucedemo.com/");

        // Login
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Add first two products
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()='Add to cart'])[1]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()='Add to cart'])[2]"))).click();

        // Go to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link"))).click();

        // Verify 2 items in cart
        int items = driver.findElements(By.className("inventory_item_name")).size();
        Assert.assertEquals(items, 2, "Cart does not contain 2 items");

        // Checkout
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();


        // Click finish
        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();

        // Verify confirmation
        String confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header"))).getText();
        Assert.assertEquals(confirmation, "Thank you for your order!");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                File src = ts.getScreenshotAs(OutputType.FILE);
                File dest = new File("screenshot_" + result.getName() + "_" + timestamp + ".png");
                Files.copy(src.toPath(), dest.toPath());
            } catch (Exception e) {
                System.out.println("Could not take screenshot: " + e.getMessage());
            }
        }
        if (driver != null) {
            //driver.quit();
        }
    }
}
