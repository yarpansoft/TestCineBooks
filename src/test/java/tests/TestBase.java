package tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public abstract class TestBase {
    WebDriver webDriver;

    @Parameters({"browserType"})
    @BeforeMethod
    public void beforeTest(@Optional("chrome") String browserType) {
        webDriver = webDriverBuilder(browserType);
        webDriverSetup();
    }


    @AfterMethod
    public void afterTest(){
        webDriver.close();
    }


    private WebDriver webDriverBuilder(String browserType) {
        switch (browserType.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "safari":
                WebDriverManager.safaridriver().setup();
                return new SafariDriver();
            default:
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
        }
    }

    private void webDriverSetup(){
        //webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public void waitSafe(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
