import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class YouTubeSearchAndFilterFeatureTest {

    WebDriver driver;

    String firstSearchTerm = "software testing";
    String screenshotPath = "C://Users//bloop//OneDrive//Desktop//search_results_test.png";

    // End of variable declarations


    // -------------------------------
    // Setup Method
    // -------------------------------

    @BeforeMethod
    public void setUp() throws InterruptedException {

        System.out.println("BeforeMethod: Opening existing Chrome browser session.");

        // Connects to already opened Chrome instance using debugger port
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        driver = new ChromeDriver(options);

        // Opens a new tab and navigates to YouTube homepage
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.youtube.com/");
        driver.manage().window().maximize();

        // Delay to allow full page load
        Thread.sleep(4000);

        System.out.println("YouTube opened successfully.");
    }

    // End of setUp


    // --------------------------------------------------------------------------------
    // Test 1: Verify that the search box accepts input and executes search correctly
    //---------------------------------------------------------------------------------
    @Test(priority = 1)
    public void verifySearchBoxAcceptsInput() throws InterruptedException {

        System.out.println("Test 1: Verify that the search box accepts input.");

        // Locating search box and entering search term
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='search_query']"));
        searchBox.sendKeys(firstSearchTerm);

        // Delay for visibility
        Thread.sleep(2000);

        // Simulates pressing Enter key to perform search
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(4000);

        // Retrieves current URL after search execution
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);

        // Verifies that URL contains encoded search term
        Assert.assertTrue(currentUrl.contains(firstSearchTerm.replace(" ", "+")),
                "Search did not execute properly.");

        System.out.println("Search executed successfully.");
    }

    // End of verifySearchBoxAcceptsInput


    // --------------------------------------------------------------------------------
    // Test 2: Verify search results appear and capture screenshot for validation
    //---------------------------------------------------------------------------------
    @Test(priority = 2)
    public void verifySearchResultsAppear() throws InterruptedException, IOException {

        System.out.println("Test 2: Verify that search results appear after performing a search.");

        // Enter search term
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='search_query']"));
        searchBox.sendKeys(firstSearchTerm);
        Thread.sleep(1000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(5000);

        // Verify results page loaded using URL check
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("search_query"),
                "Search results page did not open successfully.");

        // Capture screenshot of results page
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotPath);
        FileHandler.copy(srcFile, destFile);

        Thread.sleep(2000);

        // Verify screenshot file exists
        Assert.assertTrue(destFile.exists(),
                "Screenshot of search results was not captured successfully.");

        System.out.println("Search results appeared successfully and screenshot was captured.");
    }

    // End of verifySearchResultsAppear


    // --------------------------------------------------------------------------------
    // Test 3: Verify that the filter menu opens correctly after search
    //---------------------------------------------------------------------------------
    @Test(priority = 3)
    public void verifyFilterMenuOpens() throws InterruptedException {

        System.out.println("Test 3: Verify that the filter menu opens.");

        // Perform search
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='search_query']"));
        searchBox.sendKeys(firstSearchTerm);
        Thread.sleep(1000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(5000);

        // Click Filters button
        WebElement filterButton = driver.findElement(By.xpath("//*[@id=\"filter-button\"]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div[2]"));
        filterButton.click();
        Thread.sleep(3000);

        // Verify filter menu appears by checking Upload Date category
        WebElement uploadDateFilter = driver.findElement(By.xpath("//*[text()='Upload date']"));
        Assert.assertTrue(uploadDateFilter.isDisplayed(),
                "Filter menu did not open successfully.");

        System.out.println("Filter menu opened successfully.");
    }

    // End of verifyFilterMenuOpens


    // --------------------------------------------------------------------------------
    // Test 4: Verify that Upload Date filter can be applied successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 4)
    public void verifyUploadDateFilterWorks() throws InterruptedException {

        System.out.println("Test 4: Verify that the Upload Date filter works.");

        // Perform search
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='search_query']"));
        searchBox.sendKeys(firstSearchTerm);
        Thread.sleep(1000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(5000);

        // Open Filters
        WebElement filterButton = driver.findElement(By.xpath("//*[@id=\"filter-button\"]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div[2]"));
        filterButton.click();
        Thread.sleep(3000);

        // Verify Upload Date section exists
        WebElement uploadDate = driver.findElement(By.xpath("//*[@id=\"filter-group-name\"]/yt-formatted-string"));
        Assert.assertTrue(uploadDate.isDisplayed(),
                "Upload Date category did not appear.");

        Thread.sleep(1000);

        // Select "This week" filter option
        WebElement thisWeek = driver.findElement(By.xpath("//*[@id=\"label\"]/yt-formatted-string"));
        thisWeek.click();
        Thread.sleep(5000);

        // Verify filtered results by checking URL still reflects search
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("search_query"),
                "Upload Date filter was not applied correctly.");

        System.out.println("Upload Date filter applied successfully.");
    }

    // End of verifyUploadDateFilterWorks


    // --------------------------------------------------------------------------------
    // Test 5: Verify Type filter removes Shorts and displays only normal videos
    //---------------------------------------------------------------------------------
    @Test(priority = 5)
    public void verifyTypeFilterWorks() throws InterruptedException {

        System.out.println("Test 5: Verify that the Type filter works.");

        // Perform search
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='search_query']"));
        searchBox.sendKeys(firstSearchTerm);
        Thread.sleep(1000);
        searchBox.sendKeys(Keys.ENTER);
        Thread.sleep(5000);

        // Open Filters
        WebElement filterButton = driver.findElement(By.xpath("//*[@id=\"filter-button\"]"));
        filterButton.click();
        Thread.sleep(3000);

        // Select Video type filter
        WebElement videoType = driver.findElement(By.xpath("//*[@id=\"label\"]/yt-formatted-string"));
        videoType.click();
        Thread.sleep(5000);

        // Verify first result is a normal video (not a Short)
        WebElement firstResult = driver.findElement(By.xpath("(//a[@id='video-title'])[1]"));
        String videoLink = firstResult.getAttribute("href");

        // Normal videos contain "watch" in URL, Shorts do not
        Assert.assertTrue(videoLink.contains("watch"),
                "Filtered result is not a normal video (might be a Short).");

        System.out.println("Type filter applied successfully and only videos are shown.");
    }

    // End of verifyTypeFilterWorks


    // -------------------------------
    // Tear Down Method
    // -------------------------------

    @AfterMethod
    public void tearDown() throws InterruptedException {

        System.out.println("AfterMethod: Closing current tab after test execution.");

        Thread.sleep(3000);

        // Closes only the current tab (keeps main browser open)
        if (driver != null) {
            driver.close();
        }
    }

    // End of tearDown
}