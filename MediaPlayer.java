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


public class MediaPlayer {
    WebDriver driver;


    @BeforeMethod
    public void ChromeSetup(){
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        //options.addArguments("user-data-dir=C:\\selenium-profile");
        options.addArguments("user-data-dir=C:\\Users\\dj412\\selenium_test");
        //options.addArguments("user-data-dir=C:/Users/dj412/AppData/Local/Google/Chrome/User Data");
        options.addArguments("profile-directory=Profile 2");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.youtube.com/watch?v=p9X7lvgHqY4");

    }


    @AfterMethod
    void ChromeClose()
    {
        driver.quit();
    }

    //Pause and Play
    @Test(priority = 1)
    void PausePlay () throws  InterruptedException{

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //waits for video element be fully loaded
        Thread.sleep(2000);
        WebElement video = driver.findElement(By.cssSelector("video"));

        //PAUSES THE VIDEO

        //waits for video player
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("video")));

        //Clicks video to pause
        video.click();
        //displays that the video is paused
        System.out.println("Video is paused");
        Thread.sleep(5000);

        //PLAYS THE VIDEO

        //simulates mouse movement to view buttons again
        new Actions(driver).moveToElement(video).perform();

        // Click play button to play
        video.click();

        //displays that the video is played
        System.out.println("Video is played");
        Thread.sleep(5000);
    }

    //Volume Control
    @Test(priority = 2)
    void VolumeControl() throws InterruptedException{

        Thread.sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Lowers volume
        Thread.sleep(3000);
        js.executeScript("document.querySelector('video').volume = 0.3");
        System.out.println("Volume lowered.");
        Thread.sleep(5000);

        //Raises volume
        js.executeScript("document.querySelector('video').volume = 1.0");
        System.out.println("Volume raised.");
        Thread.sleep(5000);

        //Mutes
        js.executeScript("document.querySelector('video').muted = true;");

        Thread.sleep(5000);
        //Unmutes
        js.executeScript("document.querySelector('video').muted = false;");

        Thread.sleep(3000);
    }

    //Playback speed
    @Test(priority = 3)
    void PlaybackSpeed() throws InterruptedException{
        Thread.sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Lowers playback rate

        Thread.sleep(3000);
        js.executeScript("document.querySelector('video').playbackRate = 0.5");
        System.out.println("Speed lowered to half the speed.");
        Thread.sleep(5000);

        //Raises playback rate to 1.5x
        js.executeScript("document.querySelector('video').playbackRate = 1.5");
        System.out.println("Speed raised to 1.5x the original speed.");
        Thread.sleep(5000);

        //Rasies playback rate to 2x
        js.executeScript("document.querySelector('video').playbackRate = 2.0;");
        System.out.println("Speed is 2x the original speed.");

        Thread.sleep(5000);

        //Returns playback rate to 1.0x
        js.executeScript("document.querySelector('video').playbackRate = 1.0;");
        System.out.println("Speed is set back to original speed.");
        Thread.sleep(3000);
    }

    //Fullscreen mode
    @Test(priority = 4)
    void Fullscreen() throws InterruptedException{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Thread.sleep(5000);

        //uses action to press f since it requires "user interaction" to go into fullscreen
        Actions actions = new Actions(driver);
        actions.sendKeys("f").perform();
        Thread.sleep(5000);

        //exits full screen
        js.executeScript("document.exitFullscreen();");
        Thread.sleep(5000);
    }

    //Seek/Video Progression
    @Test(priority = 5)
    void videoSeek() throws InterruptedException{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Thread.sleep(5000);
        //skip forward in the video
        js.executeScript("document.querySelector('video').currentTime += 10");
        Thread.sleep(5000);

        //skip backward in the video
        js.executeScript("document.querySelector('video').currentTime -= 10");
        Thread.sleep(5000);

        //jumps to a specific part of the video
        js.executeScript("document.querySelector('video').currentTime = 30");
        Thread.sleep(5000);
    }
}
