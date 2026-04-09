package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Playlist {
    WebDriver driver;

    //this is a helper function for tests 3 and 4
    //will navigate to the full playlist view for the test playlist
    void FullPlaylistView() throws InterruptedException{
        //goes to the playlist video list
        driver.get("https://www.youtube.com/feed/playlists");
        Thread.sleep(2000);

        //name of target playlist
        String playlistName = "Edited Test";

        //finds the correct "View full playlist" link for target playlist
        WebElement playlistLink = driver.findElement(
                By.xpath("//ytd-rich-item-renderer[.//h3[@title='" + playlistName + "']]//a[contains(@href,'/playlist?list=')]"));
        playlistLink.click();
        Thread.sleep(2000);
    }

    //defines the driver and opens YouTube homepage
    @BeforeMethod
    public void ChromeSetup() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();

        // Connects to the already opened Chrome browser session
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        driver = new ChromeDriver(options);

        // Opens a fresh new tab for the current test
        driver.switchTo().newWindow(WindowType.TAB);
        driver.manage().window().maximize();

        //defaults all methods to the YouTube homepage
        driver.get("https://www.youtube.com");
        Thread.sleep(3000);
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

    //this will create a new playlist to save a video
    @Test(priority = 1)
    void CreatePlaylist() throws InterruptedException{
        //open the video that will be saved into a playlist
        driver.get("https://www.youtube.com/watch?v=p9X7lvgHqY4");
        Thread.sleep(3000);

        //finds and clicks the save option to being the process of creating a playlist
        WebElement saveBtn = driver.findElement(By.xpath(
                "//*[@id=\"flexible-item-buttons\"]/yt-button-view-model[1]/button-view-model/button/yt-touch-feedback-shape"));
        saveBtn.click();
        Thread.sleep(3000);

        //finds and clicks the option to save the video into a new playlist
        WebElement NewPlaylist = driver.findElement(By.cssSelector(
                "yt-panel-footer-view-model button yt-touch-feedback-shape"));
        NewPlaylist.click();

        //finds all option elements for creating a playlist including:
        //allow other users to collaborate and add into the playlist
        WebElement collaborateButton = driver.findElement(By.xpath(
                "//span[text()='Collaborate']/ancestor::button"
        ));
        //input the name of the playlist
        WebElement nameTextbox = driver.findElement(By.xpath("/html/body/ytd-app/ytd-popup-container/tp-yt-paper-dialog/yt-dialog-view-model/dialog-layout/" +
                "div[2]/div[1]/div/yt-create-playlist-dialog-form-view-model/div[1]/text-field-view-model/textarea-shape/div/textarea"));
        //create the playlist with the attributes defined
        WebElement createButton = driver.findElement(
                By.xpath("//ytd-popup-container//*[contains(text(),'Create')]/ancestor::button")
        );

        //types out the name of the playlist
        nameTextbox.sendKeys("Create Test");
        Thread.sleep(1000);

        //allows user to change the visibility of the playlist and clicks to open dropdown
        WebElement Visibility = driver.findElement(By.cssSelector("dropdown-view-model.ytDropdownViewModelHost"));
        Visibility.click();
        Thread.sleep(1000);
        //defines the visibility options given to the user.
        List<WebElement> options = driver.findElements(By.xpath(
                "//span[text()='Public' or text()='Unlisted' or text()='Private']"
        ));
        //goes through each visibility option
        //0 = public, 1 = unlisted, and 2 = Private
        for (int count =0; count < 3; count++){
            options.get(count).click();
            Thread.sleep(1000);
            //will not click the dropdown option after the second click
            if (count<2)
                Visibility.click();
            Thread.sleep(1000);
        }

        //Toggles on the collaboration button
        collaborateButton.click();
        Thread.sleep(1000);
        //toggle off the collaboration button
        collaborateButton.click();
        Thread.sleep(2000);

        //creates popup
        createButton.click();
        Thread.sleep(2000);

        //goes to the playlists webpage to view the newly created playlist
        driver.get("https://www.youtube.com/feed/playlists");
        Thread.sleep(3000);

    }

    //this method will modify the properties the playlist
    @Test(priority = 2)
    void EditPlaylist() throws InterruptedException{
        //navigates to the playlists webpage
        driver.get("https://www.youtube.com/feed/playlists");
        Thread.sleep(3000);

        //find and clicks the menu button for the playlist
        driver.findElement(By.xpath("//*[@id=\"content\"]/yt-lockup-view-model/div/div/yt-lockup-metadata" +
                "-view-model/div[2]/button-view-model/button/yt-touch-feedback-shape")).click();
        Thread.sleep(1000);

        //finds and clicks the edit option
        driver.findElement(By.xpath("//*[@id=\"contentWrapper\"]/yt-sheet-view-model/yt-contextual-sheet-" +
                "layout/div[2]/yt-list-view-model/yt-list-item-view-model[2]/div/div")).click();
        Thread.sleep(2000);

        //clears the old name
        driver.findElement(By.xpath("//*[@id=\"input-1\"]/input")).clear();
        Thread.sleep(1000);
        //rename the playlist
        driver.findElement(By.xpath("//*[@id=\"input-1\"]/input")).sendKeys("Edited Test");
        Thread.sleep(2000);

        //clears and adds description to the playlist
        driver.findElement(By.xpath("//*[@id=\"input-2\"]/input")).clear();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"input-2\"]/input")).sendKeys("This is a test");
        Thread.sleep(2000);

        String[] options = {"Public", "Unlisted","Private"};
        for (String op: options)
        {
            //clicks the privacy button to open dropdown
            driver.findElement(By.xpath("//*[@id=\"dropdown-trigger\"]/label")).click();
            Thread.sleep(1000);
            //sets the playlist public
            driver.findElement(By.
                    xpath("//*[@id='item']/tp-yt-paper-item-body//*[contains(text(),'"+ op + "')]")).click();
            Thread.sleep(2000);

        }

        //sets the playlist back to unlisted so the vote option is enabled
        driver.findElement(By.xpath("//*[@id=\"dropdown-trigger\"]/label")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='item']/tp-yt-paper-item-body//*[contains(text(),'Unlisted')]")).click();
        Thread.sleep(2000);

        //checks to see if all voting options work
        String [] voting = {"Everyone", "Off"};
        for (String op: voting)
        {
            //selects the vote option
            driver.findElement(By.xpath("//tp-yt-paper-dropdown-menu-light[@aria-label='Voting']//div[@id='dropdown-trigger']")).click();
            Thread.sleep(1000);

            //sets the voting to everyone vote
            driver.findElement(By.
                    xpath("//tp-yt-paper-item//yt-formatted-string[text()='" + op + "']")).click();
            Thread.sleep(1000);
        }

        //click the save button
        driver.findElement(By.
                xpath("//*[@id=\"actions\"]/yt-button-view-model/button-view-model/button/yt-touch-feedback-shape/div[2]")).click();
        Thread.sleep(3000);
    }


    //this method will add a video to the playlist
    @Test(priority = 3)
    void AddVideo() throws InterruptedException{
        //calls the fullview function to open the playlist
        FullPlaylistView();

        //clicks on the add video button via a js script since YouTube prevents automated clicks of the button.
        WebElement addVid = driver.findElement(By.xpath("//yt-page-header-renderer//button[contains(@aria-label,'Add videos')]"));
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addVid);
        Thread.sleep(2000);

        //switches to the same frame of the add video pop up
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                By.xpath("//iframe[contains(@src,'docs.google.com/picker')]")
        ));

        //inserts the url of the video being added
        WebElement urlTextBox =driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/div[2]/div[2]/div/div/div[3]/div/div/div[2]/div[1]/input[2]"));
        urlTextBox.sendKeys("https://youtu.be/VWDc9oyBj5Q?si=M7WCp9uSlIk_zH_C");
        Thread.sleep(1000);

        //presses enter to submit search
        urlTextBox.sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        //clicks on the video that appears from the url search
        driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/div[2]/div[3]/div/div[2]/div/div/div[2]/div[2]/div/div[2]/div[1]")).click();
        Thread.sleep(3000);

        //clicks the add button using js script
        WebElement addBtn = driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/div[2]/div[5]/div/div[3]/div/button/span[5]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
        Thread.sleep(2000);

        //switch back to default page
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
    }

    //This test method will delete a video from a playlist
    @Test(priority = 4)
    void DeleteVideo() throws InterruptedException{
        //navigates to the full view of the test playlist
        FullPlaylistView();
        Thread.sleep(2000);
        //creates wait function to wait for loading times
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //creates a list of all the video elements
        List<WebElement> videos = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.cssSelector("ytd-playlist-video-renderer"))
        );

        //gets the second video in the playlist
        WebElement video = videos.get(1);

        //finds the action button for the specified video using cssSelector
        WebElement menuButton = video.findElement(
                By.cssSelector("button[aria-label='Action menu']")
        );

        //clicks on the menu button using script since YouTube blocks this button from being clicked automatically
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuButton);
        Thread.sleep(2000);

        //Waits until the component is loaded and stores all options available in a list of Web Elements
        List<WebElement> menuItems = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("ytd-menu-service-item-renderer"))
        );

        //Find and click "Remove from playlist" option from the list of elements
        for (WebElement item : menuItems) {
            if (item.getText().contains("Remove from")) {
                item.click();
                break;
            }
        }
        Thread.sleep(2000);
    }

    //This method will delete a playlist
    @Test(priority = 5)
    void DeletePlaylist() throws InterruptedException{
        //navigates to the playlists webpage
        driver.get("https://www.youtube.com/feed/playlists");
        Thread.sleep(3000);

        //find and clicks the menu button for the playlist
        driver.findElement(By.xpath("//*[@id=\"content\"]/yt-lockup-view-model/div/div/yt-lockup-metadata" +
                "-view-model/div[2]/button-view-model/button/yt-touch-feedback-shape")).click();
        Thread.sleep(1000);

        //finds and clicks the delete option
        driver.findElement(By.xpath("//*[@id=\"contentWrapper\"]/yt-sheet-view-model/yt-contextual-sheet-" +
                "layout/div[2]/yt-list-view-model/yt-list-item-view-model[1]/div/div")).click();
        Thread.sleep(2000);
    }


}