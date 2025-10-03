package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class LoginTestMain {

    public static void main(String[] args) {
        AndroidDriver driver = null;

        try {
            // APK path
            String apkPath = "E:\\Google download qureh\\apk file\\Login Demo_1.0_APKPure.apk";
            File app = new File(apkPath);
            if (!app.exists()) {
                System.out.println("APK not found at: " + apkPath);
                return;
            }

            // Setup driver
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setDeviceName("6XRO5T8LTKH6KFPF")
                    .setAutomationName("UiAutomator2")
                    .setApp(apkPath);

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

            Thread.sleep(3000); // simple wait for app to load

            // Replace these with actual IDs from Appium Inspector
            String usernameId = "actual_username_id";
            String passwordId = "actual_password_id";
            String loginButtonId = "com.zappycode.logindemo:id/btnLogin";
            String successMessageId = "actual_success_message_id";

            // Input username and password
            
            driver.findElement(AppiumBy.id("com.zappycode.logindemo:id/etName")).sendKeys("testuser");
            driver.findElement(AppiumBy.id("com.zappycode.logindemo:id/etPassword")).sendKeys("123456");

            // Click login
            driver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.zappycode.logindemo:id/btnLogin\"]")).click();

            Thread.sleep(2000); // simple wait for result

//            // Verify success message
//            WebElement messageElement = driver.findElement(AppiumBy.id(successMessageId));
//            String actualMessage = messageElement.getText();
//
//            if ("Login Successful".equals(actualMessage)) {
//                System.out.println("✅ Login test passed!");
//            } else {
//                System.out.println("❌ Login test failed! Message: " + actualMessage);
//                takeScreenshot(driver, "LoginFailure.png");
//            }
//
      } catch (Exception e) {
            e.printStackTrace();
            if (driver != null) takeScreenshot(driver, "LoginError.png");
        } finally {
           // if (driver != null) driver.quit();
  
        }}

    // Screenshot helper
    public static void takeScreenshot(AndroidDriver driver, String fileName) {
        try {
            File src = driver.getScreenshotAs(OutputType.FILE);
            Path dest = new File(fileName).toPath();
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}