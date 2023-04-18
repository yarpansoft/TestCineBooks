package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;


public abstract class PageBase {
    protected WebDriver webDriver;
    public PageBase(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    protected void hoverElement(WebElement webElement){
        Actions action = new Actions(webDriver);
        action.moveToElement(webElement).perform();
    }

    protected void scrollToElement(WebElement webElement){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({ behavior: 'instant', block: 'center', inline: 'center' });", webElement);
        sleepSafe(3);
    }

    protected void sleepSafe(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected WebElement waitUntilElementIsClickable(WebElement webElement, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return webElement;
    }

    protected WebElement waitUntilElementIsVisible(WebElement webElement, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(ExpectedConditions.visibilityOf(webElement));
        return webElement;
    }

    protected WebElement waitUntilElementIsPresents(By by, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return webDriver.findElement(by);
    }

    protected boolean isElementPresence(WebElement webElement, int timeOutInSeconds) {
        boolean i = false;
        try {
            i = waitUntilElementIsClickable(webElement, timeOutInSeconds).isEnabled();
        } catch (Exception exp) {
            i = false;
        } finally {
            return i;
        }
    }




}
