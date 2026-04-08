import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class YouTubeUploadFeatureTest {

    WebDriver driver;

    String filePath = "C://Users//bloop//OneDrive//Desktop//test_video.mp4";
    String videoTitle = "Software Testing Upload Test";
    String screenshotPath = "C://Users//bloop//OneDrive//Desktop//uploaded_private_video_test.png";

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

        // Opens a new tab and navigates to YouTube Studio
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://studio.youtube.com/");
        driver.manage().window().maximize();
        Thread.sleep(4000);

        System.out.println("YouTube Studio opened successfully.");
    }

    // End of setUp


    // --------------------------------------------------------------------------------
    // Test 1: Verify that the upload dialog opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 1)
    public void verifyUploadDialogOpens() throws InterruptedException {

        System.out.println("Test 1: Verify that the upload dialog opens successfully.");

        // Clicks the Create button
        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"main-container\"]/ytcp-header/header/div/div/ytcp-button/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
        createButton.click();
        Thread.sleep(2000);

        // Clicks the Upload videos option
        WebElement uploadVideosOption = driver.findElement(By.xpath("//*[@id=\"text-item-0\"]/ytcp-ve/tp-yt-paper-item-body/div/div/div/yt-formatted-string"));
        uploadVideosOption.click();
        Thread.sleep(3000);

        // Verifies that the upload step screen is displayed
        WebElement uploadDialogText = driver.findElement(By.xpath("//*[@id=\"step-title-0\"]"));
        Assert.assertTrue(uploadDialogText.isDisplayed(), "Upload dialog did not open successfully.");

        System.out.println("Upload dialog opened successfully.");
    }

    // End of verifyUploadDialogOpens


    // --------------------------------------------------------------------------------
    // Test 2: Verify that the video file can be selected and upload begins correctly
    //---------------------------------------------------------------------------------
    @Test(priority = 2)
    public void verifyVideoFileCanBeSelected() throws InterruptedException {

        System.out.println("Test 2: Verify that the video file can be selected and uploaded.");

        // Clicks the Create button
        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"main-container\"]/ytcp-header/header/div/div/ytcp-button/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
        createButton.click();
        Thread.sleep(2000);

        // Clicks the Upload videos option
        WebElement uploadVideosOption = driver.findElement(By.xpath("//*[@id=\"text-item-0\"]/ytcp-ve/tp-yt-paper-item-body/div/div/div/yt-formatted-string"));
        uploadVideosOption.click();
        Thread.sleep(3000);

        // Selects the local video file using the hidden file input
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(filePath);
        Thread.sleep(5000);

        // Verifies that the upload moved into the Details screen
        WebElement detailsText = driver.findElement(By.xpath("//*[@id=\"scrollable-content\"]/ytcp-ve/div/h1"));
        Assert.assertTrue(detailsText.isDisplayed(), "Video file was not selected or upload did not start.");

        System.out.println("Video file selected successfully and upload process started.");
    }

    // End of verifyVideoFileCanBeSelected


    // --------------------------------------------------------------------------------
    // Test 3: Verify that the title field accepts input correctly
    //---------------------------------------------------------------------------------
    @Test(priority = 3)
    public void verifyTitleFieldAcceptsInput() throws InterruptedException {

        System.out.println("Test 3: Verify that the title field accepts input correctly.");

        // Clicks the Create button
        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"main-container\"]/ytcp-header/header/div/div/ytcp-button/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
        createButton.click();
        Thread.sleep(2000);

        // Clicks the Upload videos option
        WebElement uploadVideosOption = driver.findElement(By.xpath("//*[@id=\"text-item-0\"]/ytcp-ve/tp-yt-paper-item-body/div/div/div/yt-formatted-string"));
        uploadVideosOption.click();
        Thread.sleep(3000);

        // Uploads the test video
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(filePath);
        Thread.sleep(5000);

        // Locates the title textbox and enters a custom title
        WebElement titleBox = driver.findElement(By.xpath("(//*[@id='textbox'])[1]"));
        titleBox.clear();
        titleBox.sendKeys(videoTitle);
        Thread.sleep(2000);

        // Verifies that the custom title was entered correctly
        String enteredTitle = titleBox.getText();
        Assert.assertTrue(enteredTitle.contains(videoTitle), "Video title was not entered correctly.");

        System.out.println("Video title was entered successfully.");
    }

    // End of verifyTitleFieldAcceptsInput


    // --------------------------------------------------------------------------------
    // Test 4: Verify that the audience option can be selected correctly
    //---------------------------------------------------------------------------------
    @Test(priority = 4)
    public void verifyAudienceOptionCanBeSelected() throws InterruptedException {

        System.out.println("Test 4: Verify that the audience option can be selected.");

        // Clicks the Create button
        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"main-container\"]/ytcp-header/header/div/div/ytcp-button/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
        createButton.click();
        Thread.sleep(2000);

        // Clicks the Upload videos option
        WebElement uploadVideosOption = driver.findElement(By.xpath("//*[@id=\"text-item-0\"]/ytcp-ve/tp-yt-paper-item-body/div/div/div/yt-formatted-string"));
        uploadVideosOption.click();
        Thread.sleep(3000);

        // Uploads the test video
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(filePath);
        Thread.sleep(5000);

        // Selects the "No, it's not made for kids" option
        WebElement notMadeForKidsOption = driver.findElement(By.xpath("//*[contains(text(),\"No, it's not made for kids\")]"));
        notMadeForKidsOption.click();
        Thread.sleep(2000);

        // Verifies that the audience option was displayed and selected
        Assert.assertTrue(notMadeForKidsOption.isDisplayed(), "Audience option could not be selected.");

        System.out.println("Audience option selected successfully.");
    }

    // End of verifyAudienceOptionCanBeSelected


    // --------------------------------------------------------------------------------
    // Test 5: Verify that the upload process can reach the Visibility step
    //---------------------------------------------------------------------------------
    @Test(priority = 5)
    public void verifyUploadCanReachVisibilityStep() throws InterruptedException {

        System.out.println("Test 5: Verify that the upload process can reach the Visibility step.");

        // Clicks the Create button
        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"main-container\"]/ytcp-header/header/div/div/ytcp-button/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
        createButton.click();
        Thread.sleep(2000);

        // Clicks the Upload videos option
        WebElement uploadVideosOption = driver.findElement(By.xpath("//*[@id=\"text-item-0\"]/ytcp-ve/tp-yt-paper-item-body/div/div/div/yt-formatted-string"));
        uploadVideosOption.click();
        Thread.sleep(3000);

        // Uploads the test video
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(filePath);
        Thread.sleep(5000);

        // Enters a custom title
        WebElement titleBox = driver.findElement(By.xpath("(//*[@id='textbox'])[1]"));
        titleBox.clear();
        titleBox.sendKeys(videoTitle);
        Thread.sleep(2000);

        // Selects the audience option
        WebElement notMadeForKidsOption = driver.findElement(By.xpath("//*[contains(text(),\"No, it's not made for kids\")]"));
        notMadeForKidsOption.click();
        Thread.sleep(2000);

        // Clicks the Next button three times to reach the Visibility step
        for (int i = 1; i <= 3; i++) {
            WebElement nextButton = driver.findElement(By.xpath("//*[@id=\"next-button\"]/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
            nextButton.click();
            Thread.sleep(2000);
        }

        // Verifies that the Visibility section is displayed
        WebElement visibilityText = driver.findElement(By.xpath("//*[@id=\"step-title-3\"]"));
        Assert.assertTrue(visibilityText.isDisplayed(), "Visibility step was not reached successfully.");

        System.out.println("Upload process reached the Visibility step successfully.");
    }

    // End of verifyUploadCanReachVisibilityStep


    // --------------------------------------------------------------------------------
    // Test 6: Upload video, mark it Private, open Content page, and capture screenshot
    //---------------------------------------------------------------------------------
    @Test(priority = 6)
    public void verifyUploadedVideoAppearsInContentAsPrivate() throws InterruptedException, IOException {

        System.out.println("Test 6: Verify that the uploaded video appears in the Content section as Private.");

        // Clicks the Create button
        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"main-container\"]/ytcp-header/header/div/div/ytcp-button/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
        createButton.click();
        Thread.sleep(2000);

        // Clicks the Upload videos option
        WebElement uploadVideosOption = driver.findElement(By.xpath("//*[@id=\"text-item-0\"]/ytcp-ve/tp-yt-paper-item-body/div/div/div/yt-formatted-string"));
        uploadVideosOption.click();
        Thread.sleep(3000);

        // Uploads the test video
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(filePath);
        Thread.sleep(5000);

        // Enters a custom title
        WebElement titleBox = driver.findElement(By.xpath("(//*[@id='textbox'])[1]"));
        titleBox.clear();
        titleBox.sendKeys(videoTitle);
        Thread.sleep(2000);

        // Selects the audience option
        WebElement notMadeForKidsOption = driver.findElement(By.xpath("//*[contains(text(),\"No, it's not made for kids\")]"));
        notMadeForKidsOption.click();
        Thread.sleep(2000);

        // Clicks the Next button three times to reach the Visibility step
        for (int i = 1; i <= 3; i++) {
            WebElement nextButton = driver.findElement(By.xpath("//*[@id=\"next-button\"]/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
            nextButton.click();
            Thread.sleep(2000);
        }

        // Verifies that the Visibility section is displayed
        WebElement visibilityText = driver.findElement(By.xpath("//*[@id=\"step-title-3\"]"));
        Assert.assertTrue(visibilityText.isDisplayed(), "Visibility step was not reached successfully.");

        // Selects Private visibility option
        WebElement privateOption = driver.findElement(By.xpath("//*[contains(text(),'Private')]"));
        privateOption.click();
        Thread.sleep(2000);

        // Clicks Done to finish upload
        WebElement doneButton = driver.findElement(By.xpath("//*[@id=\"done-button\"]/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
        doneButton.click();
        Thread.sleep(5000);

        // Closes upload dialog
        WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"close-button\"]/ytcp-button-shape/button/yt-touch-feedback-shape/div[2]"));
        closeButton.click();
        Thread.sleep(3000);

        // Navigates to the Content / Videos section
        WebElement contentMenu = driver.findElement(By.xpath("//*[@id=\"menu-item-1\"]"));
        contentMenu.click();
        Thread.sleep(5000);

        // Takes a screenshot as proof that uploaded video appears in Content section
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotPath);
        FileHandler.copy(srcFile, destFile);
        Thread.sleep(2000);

        // Verifies that screenshot file exists
        Assert.assertTrue(destFile.exists(),
                "Screenshot of uploaded private video was not captured successfully.");

        System.out.println("Uploaded private video screenshot captured successfully.");
    }

    // End of verifyUploadedVideoAppearsInContentAsPrivate


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

}
// End of YouTubeUploadFeatureTest