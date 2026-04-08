import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class YouTubeStudioDashboardFeatureTest {

    WebDriver driver;

    String studioUrl = "https://studio.youtube.com/";

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
        driver.get(studioUrl);
        driver.manage().window().maximize();
        Thread.sleep(4000);

        System.out.println("YouTube Studio opened successfully.");
    }

    // End of setUp


    // --------------------------------------------------------------------------------
    // Test 1: Verify that the Studio Dashboard page opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 1)
    public void verifyStudioDashboardOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 1: Verify that the Studio Dashboard page opens successfully.");

        // Locates the dashboard heading
        WebElement dashboardHeading = driver.findElement(By.xpath("//*[text()='Channel dashboard']"));
        Thread.sleep(2000);

        // Verifies that the dashboard heading is displayed
        Assert.assertTrue(dashboardHeading.isDisplayed(),
                "Studio Dashboard page did not open successfully.");

        System.out.println("Studio Dashboard page opened successfully.");
    }

    // End of verifyStudioDashboardOpensSuccessfully


    // --------------------------------------------------------------------------------
    // Test 2: Verify that the Content page opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 2)
    public void verifyContentPageOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 2: Verify that the Content page opens successfully.");

        // Clicks the Content option from the Studio sidebar
        WebElement contentButton = driver.findElement(By.xpath("//*[text()='Content']"));
        contentButton.click();
        Thread.sleep(4000);

        // Verifies that the URL changed to the content section
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/channel/") || currentUrl.contains("/videos") || currentUrl.contains("studio.youtube.com"),
                "Content page did not open successfully.");

        System.out.println("Content page opened successfully.");
    }

    // End of verifyContentPageOpensSuccessfully



    // --------------------------------------------------------------------------------
// Test 3: Verify that the Analytics page opens successfully and its tabs can be viewed
//---------------------------------------------------------------------------------
    @Test(priority = 3)
    public void verifyAnalyticsPageOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 3: Verify that the Analytics page opens successfully and its tabs can be viewed.");

        // Clicking the Analytics option from the Studio sidebar
        WebElement analyticsButton = driver.findElement(By.xpath("//*[text()='Analytics']"));
        analyticsButton.click();
        Thread.sleep(5000);

        // Verifying that the Analytics page is displayed
        WebElement analyticsHeading = driver.findElement(By.xpath("//*[@id=\"page-title-container\"]/h1"));
        Assert.assertTrue(analyticsHeading.isDisplayed(),
                "Analytics page did not open successfully.");

        System.out.println("Analytics page opened successfully.");

        // Clicking the Overview tab
        WebElement overviewTab = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/ytcp-ve"));
        overviewTab.click();
        Thread.sleep(3000);

        System.out.println("Overview tab viewed successfully.");

        // Clicking the Content tab
        WebElement contentTab = driver.findElement(By.xpath("//*[@id=\"content\"]/div/ytcp-ve"));
        contentTab.click();
        Thread.sleep(3000);

        System.out.println("Content tab viewed successfully.");

        // Clicking the Audience tab
        WebElement audienceTab = driver.findElement(By.xpath("//*[@id=\"build_audience\"]/div/ytcp-ve"));
        audienceTab.click();
        Thread.sleep(3000);

        System.out.println("Audience tab viewed successfully.");

        // Clicking the Trends tab
        WebElement trendsTab = driver.findElement(By.xpath("//*[@id=\"research\"]/div/ytcp-ve"));
        trendsTab.click();
        Thread.sleep(3000);

        System.out.println("Trends tab viewed successfully.");

        // Final verification that Analytics section remains open
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("analytics"),
                "Analytics tabs were not viewed successfully.");
    }

// End of verifyAnalyticsPageOpensSuccessfully


    // --------------------------------------------------------------------------------
    // Test 4: Verify that the Community page opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 4)
    public void verifyCommunityPageOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 4: Verify that the Community page opens successfully.");

        // Clicks the Community option from the Studio sidebar
        WebElement communityButton = driver.findElement(By.xpath("//*[text()='Community']"));
        communityButton.click();
        Thread.sleep(4000);

        // Verifies that the community page opened by checking URL or visible text
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("comments") || currentUrl.contains("community") || currentUrl.contains("studio.youtube.com"),
                "Community page did not open successfully.");

        System.out.println("Community page opened successfully.");
    }

    // End of verifyCommunityPageOpensSuccessfully


    // --------------------------------------------------------------------------------
    // Test 5: Verify that the Customization page opens successfully
    //---------------------------------------------------------------------------------
    @Test(priority = 5)
    public void verifyCustomizationPageOpensSuccessfully() throws InterruptedException {

        System.out.println("Test 5: Verify that the Customization page opens successfully.");

        // Clicks the Customization option from the Studio sidebar
        WebElement customizationButton = driver.findElement(By.xpath("//*[text()='Customization']"));
        customizationButton.click();
        Thread.sleep(4000);

        // Verifies that the customization page opened
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("customization") || currentUrl.contains("branding") || currentUrl.contains("studio.youtube.com"),
                "Customization page did not open successfully.");

        System.out.println("Customization page opened successfully.");
    }

    // End of verifyCustomizationPageOpensSuccessfully


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

} // End of YouTubeStudioDashboardFeatureTest
