package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class Comment {
    WebDriver driver;

    //function used to actually scroll the webpage down to the comment section
    //separate from the before method function since the last method will require a different video
    void PageSetup() throws InterruptedException {
        // wait used for video element be fully loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(1000);

        // finds the video element on the page
        WebElement video = driver.findElement(By.cssSelector("video"));

        // waits for video player
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("video")));

        // pauses the video using JavaScript (more reliable than clicking)
        ((JavascriptExecutor) driver).executeScript("arguments[0].pause();", video);
        Thread.sleep(1000);

        // scrolls down to the comment section
        JavascriptExecutor scroll = (JavascriptExecutor) driver;
        scroll.executeScript("window.scroll(0,500)", "");
        Thread.sleep(1000);
    }

    //The function will set up the video webpage to pause the video and scroll down to the comment section
    @BeforeMethod
    public void ChromeSetup() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();

        // Connects to the already opened Chrome browser session
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        driver = new ChromeDriver(options);

        // Opens a fresh new tab for the current test
        driver.switchTo().newWindow(WindowType.TAB);
        driver.manage().window().maximize();

        driver.get("https://www.youtube.com/watch?v=p9X7lvgHqY4");
        Thread.sleep(2000);
        PageSetup(); //calls page set up
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

    //This method will create the comment
    @Test (priority = 1)
    void CreateComment() throws InterruptedException {
        //clicks on the placeholder area for the typebox to activate it
        driver.findElement(By.xpath("//*[@id=\"placeholder-area\"]")).click();
        Thread.sleep(2000);

        //types the comment for the video
        driver.findElement(By.xpath("//*[@id='contenteditable-root']")).sendKeys("Cool Vid.");
        Thread.sleep(2000);

        //finds and clicks the submit comment button
        WebElement submit = driver.findElement(By.
                xpath("//*[@id='submit-button']/yt-button-shape/button/yt-touch-feedback-shape"));
        submit.click();
        Thread.sleep(3000);
    }

    //This method will test the like and dislike functionality
    @Test (priority = 2)
    void LikeDislikeComment() throws InterruptedException {
        //finds the like and dislike buttons
        WebElement likeBtn = driver.findElement(By.xpath("//*[@id='like-button']"));
        WebElement disLikeBtn = driver.findElement(By.xpath("//*[@id='dislike-button']"));

        //clicks the like button twice to like and unlike the comment
        for (int count =0; count < 2; count++)
        {
            //likes comment
            likeBtn.click();
            Thread.sleep(2000);
        }

        //same as the before, but now with the dislike button
        for (int count =0; count < 2; count++)
        {
            //dislikes comment
            disLikeBtn.click();
            Thread.sleep(2000);
        }

        //in this segment, we will test if Youtube allows user to both like and dislike a comment
        disLikeBtn.click();
        Thread.sleep(2000);
        //will try to like a disliked comment
        likeBtn.click();

        //shows that it will instead remove the dislike and like the video
        Thread.sleep(3000);
    }

    //this method will test the reply to comment functionality
    @Test (priority = 3)
    void ReplyComment() throws InterruptedException {

        //finds and clicks the comment reply button
        WebElement replyBtn = driver.findElement(By.xpath("//*[@id=\"reply-button-end\"]"));
        replyBtn.click();
        Thread.sleep(2000);

        //finds and clicks the cancel button to cancel the creation of the reply
        WebElement cancelBtn = driver.findElement(By.
                xpath("//*[@id='cancel-button']/yt-button-shape/button/yt-touch-feedback-shape"));
        WebElement replyBox = driver.findElement(By.xpath("//*[@id='contenteditable-root']"));
        //cancels creation of reply
        cancelBtn.click();
        Thread.sleep(2000);

        //creates reply
        replyBtn.click();
        Thread.sleep(2000);
        replyBox.sendKeys("Good comment.");
        //finds and clicks the confirm button
        WebElement confirmBtn = driver.findElement(By.
                xpath("//*[@id='submit-button']/yt-button-shape/button/yt-touch-feedback-shape"));
        Thread.sleep(1000);
        confirmBtn.click();
        Thread.sleep(3000);
    }

    //This method will test the edit functionality
    @Test (priority = 4)
    void EditComment() throws InterruptedException {
        // clicks the menu button (3 dots) for the comment
        driver.findElement(By.xpath("//*[@id=\"action-menu\"]")).click();
        Thread.sleep(2000);

        // clicks on the edit comment option
        driver.findElement(By.xpath("//*[@id=\"items\"]/ytd-menu-navigation-item-renderer[2]/a/tp-yt-paper-item/yt-formatted-string")).click();
        Thread.sleep(2000);

        // waits for the edit textbox to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement commentBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='contenteditable-root']"))
        );

        // clears the comment and replaces the comment text
        commentBox.clear();
        commentBox.sendKeys("Great Video!");
        Thread.sleep(2000);

        // clicks confirm button to edit comment
        WebElement submitEditButton = driver.findElement(
                By.xpath("//*[@id=\"submit-button\"]/yt-button-shape/button/yt-touch-feedback-shape/div[2]")
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitEditButton);

        Thread.sleep(3000);
    }

    //This method will test the deletion functionality
    @Test (priority = 5)
    void DeleteComment() throws InterruptedException {

        //defines the comment menu for a comment
        WebElement options = driver.findElement(By.xpath("//*[@id='action-menu']"));

        //clicks to view the options
        options.click();
        Thread.sleep(2000);

        //defines the delete option for comments
        WebElement deleteBtn = driver.findElement(
                By.xpath("//*[@id=\"items\"]/ytd-menu-navigation-item-renderer[3]/a/tp-yt-paper-item/yt-formatted-string"));
        deleteBtn.click();
        Thread.sleep(2000);

        //cancels the delete option using the cancel button
        WebElement cancelDeleteButton = driver.findElement(
                By.xpath("//*[@id='cancel-button']/yt-button-shape/button")
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelDeleteButton);
        Thread.sleep(2000);

        //clicks options again to delete comment
        options.click();
        Thread.sleep(2000);
        deleteBtn.click();
        Thread.sleep(2000);

        //confirms deletion of comment
        WebElement confirmDeleteButton = driver.findElement(
                By.xpath("//*[@id=\"confirm-button\"]/yt-button-shape/button/yt-touch-feedback-shape/div[2]")
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmDeleteButton);
        Thread.sleep(4000);
    }

    //This method will test the sort by functionality
    @Test (priority = 6)
    void CommentsSort() throws InterruptedException {
        // changes to a different video with more comments
        driver.get("https://www.youtube.com/watch?v=WIRK_pGdIdA");
        PageSetup();

        // creates wait function for the sort menu
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // finds and clicks the Sort by button to view the options
        WebElement sortBy = driver.findElement(By.xpath("//*[@id=\"trigger\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sortBy);
        Thread.sleep(2000);

        // defines the Top comments and Newest first menu options
        WebElement topComments = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"menu\"]/a[1]"))
        );
        WebElement newestComment = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"menu\"]/a[2]"))
        );

        // sorts comments by Newest first
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newestComment);
        Thread.sleep(3000);

        // opens sort menu again
        WebElement sortByAgain = driver.findElement(By.xpath("//*[@id=\"trigger\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sortByAgain);
        Thread.sleep(2000);

        // sorts comments back to Top comments
        WebElement topCommentsAgain = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"menu\"]/a[1]"))
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", topCommentsAgain);
        Thread.sleep(3000);
    }
}