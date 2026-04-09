package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Channel {

    WebDriver driver;

    String channelUrl = "https://www.youtube.com/@NintendoAmerica";
    String expectedChannelName = "Nintendo of America";

    // End of variable declarations


    // -------------------------------
    // Setup Method
    // -------------------------------

    @BeforeMethod
    public void setUp() throws InterruptedException {

        System.out.println("BeforeMethod: Opening existing Chrome browser session.");

        // Connects to the already opened Chrome instance using debugger port
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        driver = new ChromeDriver(options);

        // Opens a new tab and navigates to the selected YouTube channel
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(channelUrl);
        driver.manage().window().maximize();
        Thread.sleep(4000);

        System.out.println("Nintendo America channel opened successfully.");
    }

    // End of setUp


    // --------------------------------------------------------------------------------
    // Test 1: Verify that the channel page opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 1)
    public void verifyChannelPageOpensSuccessfully() {

        System.out.println("Test 1: Verify that the channel page opens successfully.");

        // Locates the channel name on the page
        WebElement channelName = driver.findElement(By.xpath("//*[text()='Nintendo of America']"));

        // Verifies that the correct channel page is displayed
        Assert.assertTrue(channelName.isDisplayed(), "Channel page did not open successfully.");

        System.out.println("Channel page opened successfully.");
    }

    // End of verifyChannelPageOpensSuccessfully


    // --------------------------------------------------------------------------------
    // Test 2: Verify that the Home tab opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 2)
    public void verifyHomeTabOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 2: Verify that the Home tab opens successfully.");

        // Clicks the Home tab
        WebElement homeTab = driver.findElement(By.xpath("//*[@id=\"tabsContent\"]/yt-tab-group-shape/div[1]/yt-tab-shape[1]/div[1]"));
        homeTab.click();
        Thread.sleep(3000);

        // Verifies that the URL remains on the channel page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("@NintendoAmerica"),
                "Home tab did not open successfully.");

        System.out.println("Home tab opened successfully.");
    }

    // End of verifyHomeTabOpensSuccessfully


    // --------------------------------------------------------------------------------
    // Test 3: Verify that the Videos tab opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 3)
    public void verifyVideosTabOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 3: Verify that the Videos tab opens successfully.");

        // Clicks the Videos tab
        WebElement videosTab = driver.findElement(By.xpath("//*[text()='Videos']"));
        videosTab.click();
        Thread.sleep(3000);

        // Verifies that the URL now includes the videos tab
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/videos"),
                "Videos tab did not open successfully.");

        System.out.println("Videos tab opened successfully.");
    }

    // End of verifyVideosTabOpensSuccessfully


    // --------------------------------------------------------------------------------
    // Test 4: Verify that the Shorts tab opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 4)
    public void verifyShortsTabOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 4: Verify that the Shorts tab opens successfully.");

        // Clicks the Shorts tab
        WebElement shortsTab = driver.findElement(By.xpath("//*[@id=\"tabsContent\"]/yt-tab-group-shape/div[1]/yt-tab-shape[3]/div[1]"));
        shortsTab.click();
        Thread.sleep(3000);

        // Verifies that the URL now includes the shorts tab
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/shorts"),
                "Shorts tab did not open successfully.");

        System.out.println("Shorts tab opened successfully.");
    }

    // End of verifyShortsTabOpensSuccessfully


    // --------------------------------------------------------------------------------
    // Test 5: Verify that the Channel Description opens correctly
    //---------------------------------------------------------------------------------
    @Test(priority = 5)
    public void verifyChannelDescriptionOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 5: Verify that the Channel Description opens correctly.");

        // Clicking the Channel Description
        WebElement channelDescription = driver.findElement(By.xpath("//*[@id=\"page-header\"]/yt-page-header-renderer/yt-page-header-view-model/div/div[1]/div/yt-description-preview-view-model/truncated-text/button/span/span"));
        channelDescription.click();
        Thread.sleep(3000);

        // Verifying that the description section is displayed
        WebElement descriptionSection = driver.findElement(By.xpath("//*[@id=\"title-text\"]"));
        Assert.assertTrue(descriptionSection.isDisplayed(),
                "Channel Description did not open successfully.");

        System.out.println("Channel Description opened successfully.");
    }

    // End of verifyChannelDescriptionOpensSuccessfully


    // --------------------------------------------------------------------------------
    // Test 6: Verify subscribe → unsubscribe flow and sidebar update
    //---------------------------------------------------------------------------------
    @Test(priority = 6)
    public void verifySubscribeAndUnsubscribeFromChannel() throws InterruptedException {

        System.out.println("Test 6: Verify full subscribe → unsubscribe flow.");

        // Creating an explicit wait for dynamic elements
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Locating the Subscribe or Subscribed button
        WebElement subscribeButton = driver.findElement(By.xpath("//*[text()='Subscribe' or text()='Subscribed']"));

        String buttonText = subscribeButton.getText();
        System.out.println("Current button state: " + buttonText);

        // Scrolling to ensure the button is visible before interaction
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subscribeButton);
        Thread.sleep(3000);

        // Subscribing to the channel if not already subscribed
        if (buttonText.equalsIgnoreCase("Subscribe")) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subscribeButton);
            Thread.sleep(6000);
            System.out.println("Subscribed to channel successfully.");
        } else {
            System.out.println("Channel was already subscribed.");
        }

        // Verifying that the channel appears in the Subscriptions sidebar
        WebElement subscribedChannel = driver.findElement(
                By.xpath("//yt-formatted-string[text()='Nintendo of America']")
        );
        Assert.assertTrue(subscribedChannel.isDisplayed(),
                "Subscribed channel was not found in the Subscriptions sidebar.");

        System.out.println("Channel appears in the Subscriptions sidebar successfully.");
        Thread.sleep(3000);

        // Clicking the "Subscribed" button to open the unsubscribe menu
        WebElement subscribedButton = driver.findElement(By.xpath("//*[text()='Subscribed']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subscribedButton);
        Thread.sleep(3000);

        // Waiting for the unsubscribe option to appear
        WebElement unsubscribeMenuOption = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Unsubscribe']"))
        );

        // Clicking the unsubscribe option
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", unsubscribeMenuOption);
        Thread.sleep(3000);

        // Waiting for confirmation dialog
        WebElement confirmButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"confirm-button\"]//button")
                )
        );

        // Clicking final confirmation button
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmButton);
        Thread.sleep(6000);

        System.out.println("Channel unsubscribed successfully.");

        // Verifying that the channel no longer appears in the sidebar
        boolean stillExists = driver.findElements(
                By.xpath("//yt-formatted-string[text()='Nintendo of America']")
        ).size() > 0;

        Assert.assertFalse(stillExists,
                "Channel still appears in the Subscriptions sidebar after unsubscribing.");

        System.out.println("Channel successfully removed from the Subscriptions sidebar.");
    }

    // End of verifySubscribeAndUnsubscribeFromChannel


    // -------------------------------
    // Tear Down Method
    // -------------------------------

    @AfterMethod
    public void tearDown() throws InterruptedException {

        System.out.println("AfterMethod: Closing current tab after test execution.");
        Thread.sleep(3000);

        // Closes only the current tab while keeping the main browser session alive
        if (driver != null) {
            driver.close();
        }
    }

    // End of tearDown

} // End of YouTubeChannelFeatureTest

