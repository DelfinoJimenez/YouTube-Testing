package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class HomePage {
    WebDriver driver;

    //
    @BeforeMethod
    public void ChromeSetup() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();

        // Connects to the already opened Chrome browser session
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        driver = new ChromeDriver(options);

        // Opens a fresh new tab for the current test
        driver.switchTo().newWindow(WindowType.TAB);
        driver.manage().window().maximize();

        driver.get("https://www.youtube.com");
    }

    //The function will close only the current tab after each method
    @AfterMethod
    void ChromeClose() throws InterruptedException
    {
        Thread.sleep(2000);

        if (driver != null) {
            driver.close();
        }
    }

    //This test method will select the first valid video that appears on the homepage
    @Test(priority = 1)
    void SelectingVideo() throws InterruptedException {
        Thread.sleep(4000); //used to wait until a specific element is done loading
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        //waits for any videos to load onto YouTube then creates a list of the video links
        List<WebElement> videos = driver.findElements(By.cssSelector("a[href*='watch']"));
        //Will scan list to click first valid video link

        for (WebElement video : videos) {
            String href = video.getAttribute("href");

            //Skip if parent contains an ad
            WebElement richItem = video.findElement(By.xpath("./ancestor::ytd-rich-item-renderer"));
            if (!richItem.findElements(By.cssSelector("ytd-ad-slot-renderer")).isEmpty()) {
                continue;
            }

            if (href != null && href.contains("watch") && video.isDisplayed())
            {
                video.click();
                //waits for 5 seconds to show the video is playing before leaving loop
                Thread.sleep(5000);
                break;
            }
        }
    }

    //This method will ensure that the options on the side of the 'You' section of the sidebar
    //redirect to appropriate webpage this includes: history, likes, playlists, etc.
    @Test(priority = 2)
    void SideBar() throws InterruptedException{
        //define variables to hold expected and actual URLs
        String currentUrl = "";
        String expectedUrl = "";

        Thread.sleep(3000);

        //Will check if the URL matches the expected URL for the history tab
        driver.findElement(By.xpath("//a[@title='History']")).click();
        Thread.sleep(3000);
        //grabs and asserts the URLs match
        currentUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.youtube.com/feed/history";
        Assert.assertEquals(currentUrl, expectedUrl, "URL does not match expected URL");

        Thread.sleep(1000);

        //Will check if the URL matches the expected URL for the playlist tab
        driver.findElement(By.xpath("//a[@title='Playlists']")).click();
        Thread.sleep(3000);
        //grabs and asserts the URLs match
        currentUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.youtube.com/feed/playlists";
        Assert.assertEquals(currentUrl, expectedUrl, "URL does not match expected URL");

        Thread.sleep(1000);

        //Will check if the URL matches the expected URL for the Watch later tab
        driver.findElement(By.xpath("//a[@title='Watch later']")).click();
        Thread.sleep(3000);
        //grabs and asserts the URLs match
        currentUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.youtube.com/playlist?list=WL";
        Assert.assertEquals(currentUrl, expectedUrl, "URL does not match expected URL");

        Thread.sleep(1000);

        //Will check if the URL matches the expected URL for the Liked videos tab
        driver.findElement(By.xpath("//a[@title='Liked videos']")).click();
        Thread.sleep(3000);
        //grabs and asserts the URLs match
        currentUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.youtube.com/playlist?list=LL";
        Assert.assertEquals(currentUrl, expectedUrl, "URL does not match expected URL");

        Thread.sleep(1000);

        //Will check if the URL matches the expected URL for the Home tab
        driver.findElement(By.xpath("//a[@title='Home']")).click();
        Thread.sleep(3000);
        //grabs and asserts the URLs match
        currentUrl = driver.getCurrentUrl();
        expectedUrl = "https://www.youtube.com/";
        Assert.assertEquals(currentUrl, expectedUrl, "URL does not match expected URL");

        Thread.sleep(1000);
    }

    //This method will demonstrate the homepage of YouTube loads infinitely
    //by scrolling for a total of 6000 pixels
    @Test(priority = 3)
    void Scroll() throws InterruptedException{
        JavascriptExecutor scroll = (JavascriptExecutor) driver;
        Thread.sleep(3000);
        for (int count =0; count <= 10; count++)
        {
            scroll.executeScript("window.scrollBy(0,arguments[0]);", 600);
            Thread.sleep(1000);
        }
        Thread.sleep(2000);
    }

    //This method will test if the homepage categories are displaying videos for each category
    @Test(priority = 4)
    void HomepageCategories() throws InterruptedException{
        Thread.sleep(3000);

        //Finds all category titles displayed above homepage videos
        List<WebElement> chips = driver.findElements(By.xpath("//*[@id='chips']/yt-chip-cloud-chip-renderer"));

        for (WebElement chip : chips) {
            try {
                chip.click();
            } catch (ElementClickInterceptedException e) {
                driver.findElement(By.xpath("//button[@aria-label='Next']")).click();
                chip.click();
            }
            Thread.sleep(2000);//wait for videos to load
        }

    }

    //This method will test if the user can access settings from the account avatar in the homepage
    @Test(priority = 5)
    void ProfileIcon() throws InterruptedException{
        Thread.sleep(2000);

        //clicks on the avatar icon for the user
        driver.findElement(By.xpath("//*[@id=\"avatar-btn\"]")).click();
        Thread.sleep(2000);

        //Find all menu sections for the user
        List<WebElement> accountOp = driver.findElements(By.cssSelector("yt-multi-page-menu-section-renderer"));
        //displays all options
        for (WebElement webElement : accountOp) {
            System.out.println(webElement.getText());
        }

        //selects the setting option
        accountOp.get(3).click();
        Thread.sleep(2000);
    }

}