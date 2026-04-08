import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Comments {
    WebDriver driver;

    //function used to actually scroll the webpage down to the comment section
    //separate from the before method function since the last method will require a different video
    void PageSetup() throws InterruptedException {
        //wait used for video element be fully loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(1000);
        //finds the video element on the page
        WebElement video = driver.findElement(By.cssSelector("video"));

        //PAUSES THE VIDEO
        //simulates mouse movement to see video options.
        new Actions(driver).moveToElement(video).perform();
        //waits for video player
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("video")));
        //Clicks video to pause
        video.click();
        Thread.sleep(1000);

        //scrolls down to the comment section of the video
        JavascriptExecutor scroll = (JavascriptExecutor) driver;
        scroll.executeScript("window.scroll(0,500)", "");
        Thread.sleep(1000);
    }

    //The function will set up the video webpage to pause the video and scroll down to the comment section
    @BeforeMethod
    public void ChromeSetup() throws InterruptedException{
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("user-data-dir=C:\\Users\\dj412\\selenium_test");
        options.addArguments("profile-directory=Profile 2");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.youtube.com/watch?v=p9X7lvgHqY4");
        PageSetup();//calls page set up
    }

    //The function will close the driver after each method
    @AfterMethod
    void ChromeClose()
    {
        driver.quit();
    }

    //This method will create the comment
    @Test(priority = 1)
    void CreateComment() throws InterruptedException{
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
    @Test(priority = 2)
    void LikeDislikeComment() throws InterruptedException{
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
    @Test(priority = 3)
    void ReplyComment() throws InterruptedException{

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
    @Test(priority = 4)
    void EditComment() throws InterruptedException{
        //clicks the menu button (3 dots) for the comment
        driver.findElement(By.xpath("//*[@id=\"action-menu\"]")).click();
        Thread.sleep(2000);

        //clicks on the edit comment option
        driver.findElement(By.
                xpath("//*[@id=\"items\"]/ytd-menu-navigation-item-renderer[1]/a/tp-yt-paper-item")).click();
        Thread.sleep(2000);

        //finds the textbox for the comment
        WebElement commentBox = driver.findElement(By.xpath("//*[@id='contenteditable-root']"));
        //clears the comment and replaces the comment's text
        commentBox.clear();
        commentBox.sendKeys("Great Video!");
        Thread.sleep(2000);

        //clicks confirm button to edit comment
        driver.findElement(By.
                xpath("//*[@id=\"submit-button\"]/yt-button-shape/button/yt-touch-feedback-shape/div[2]"))
                .click();

        Thread.sleep(3000);
    }

    //This method will test the deletion functionality
    @Test(priority = 5)
    void DeleteComment() throws InterruptedException{

        //defines the comment menu for a comment
        WebElement options = driver.findElement(By.xpath("//*[@id='action-menu']"));

        //clicks to view the options
        options.click();
        Thread.sleep(2000);

        //defines the delete option for comments
        WebElement deleteBtn = driver.findElement(
                By.xpath("//*[@id='items']/ytd-menu-navigation-item-renderer[2]/a"));
        deleteBtn.click();
        Thread.sleep(2000);

        //cancels the delete option using the cancel button
        driver.findElement(By.xpath("//*[@id='cancel-button']/yt-button-shape")).click();
        Thread.sleep(2000);

        //clicks options again to delete comment
        options.click();
        Thread.sleep(2000);
        deleteBtn.click();
        Thread.sleep(2000);

        //confirms deletion of comment
        driver.findElement(By.xpath("//*[@id='confirm-button']/yt-button-shape")).click();
        Thread.sleep(4000);
    }

    //This method will test the sort by functionality
    @Test(priority = 6)
    void CommentsSort() throws InterruptedException{
        //changes to a different video with more comments
        driver.get("https://www.youtube.com/watch?v=WIRK_pGdIdA");
        PageSetup();

        //finds and clicks the sort by button to view the options
        WebElement SortBy = driver.findElement(By.xpath("//*[@id=\"trigger\"]"));
        SortBy.click();
        //defines the top and newest comments buttons
        WebElement TopComments = driver.findElement(By.
                xpath("//*[@id=\"menu\"]/a[1]/tp-yt-paper-item/tp-yt-paper-item-body"));
        WebElement NewestComment = driver.findElement(By.
                xpath("//*[@id=\"menu\"]/a[2]/tp-yt-paper-item/tp-yt-paper-item-body"));

        //Sorts comments by Newest
        NewestComment.click();
        Thread.sleep(2000);

        SortBy.click();
        //Sorts by Top (is the default)
        TopComments.click();
        Thread.sleep(3000);
    }
}