package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class PagePlayer extends PageBase {
    private final String baseUrl = "https://cine-books.com/";
    private final String playerFrameXpath = "//div[contains(@class, 'play-block-center--ready')]";
    @FindBy(xpath = playerFrameXpath)
    private WebElement playerFrame;
    @FindBy(xpath = "//button[@id='gdpr-cookie-accept']")
    private WebElement buttonAcceptCookies;
    @FindBy(xpath = "//button[@class='play-block-center__button']")
    private WebElement buttonPlayCenter;
    @FindBy(xpath = "//button[contains(@class, 'cn-webviewer-icon-pause')]")
    private WebElement buttonPause;
    @FindBy(xpath = "//div[@class='play-block']//button[contains(@class, 'cn-webviewer-icon-play')]")
    private WebElement buttonPlay;
    @FindBy(xpath = "//button[contains(@class, 'cn-webviewer-icon-next')]")
    private WebElement buttonPageNext;
    @FindBy(xpath = "//button[contains(@class, 'cn-webviewer-icon-previous')]")
    private WebElement buttonPagePrev;
    @FindBy(xpath = "(//div[contains(@class, 'frame-number')])[1]")
    private WebElement pageCurrent;
    @FindBy(xpath = "(//time[contains(@datetime, 'P')])[1]")
    private WebElement timingCurrent;
    @FindBy(xpath = "//div[@class='duration-wrapper']//div[@class='rc-slider-handle']")
    private WebElement sliderCurrent;
    private final By sliderCurrentBy = By.xpath("//div[@class='duration-wrapper']//div[@class='rc-slider-handle']");
    private final By playerSubtitles = By.xpath("//div[contains(@class, 'video_subtitles')]");
    private int beforeTime;
    private float beforeProgress;
    private int beforePage;
    private int beforePlayerChunk;
    private float beforePlayerTime;
    private int beforeNetworkChunkNumber;
    private String beforeNetworkChunkLast;



    public PagePlayer(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void openPlayer() {
        webDriver.navigate().to(baseUrl);
        waitUntilElementIsClickable(buttonAcceptCookies, 10).click();
        scrollToElement(waitUntilElementIsPresents(By.xpath(playerFrameXpath),10));
    }

    public void playVideo(int playTimeInSeconds) {
        waitUntilElementIsClickable(buttonPlayCenter, 10).click();
        sleepSafe(playTimeInSeconds);
    }

    public void clickButtonPause() {
        hoverElement(playerFrame);
        buttonPause.click();
        sleepSafe(1);
    }

    public void clickButtonPlay() {
        hoverElement(playerFrame);
        buttonPlay.click();
        sleepSafe(1);
    }

    public void clickButtonPageNext() {
        hoverElement(playerFrame);
        buttonPageNext.click();
        sleepSafe(1);
    }

    public void clickButtonPagePrev() {
        hoverElement(playerFrame);
        buttonPagePrev.click();
        sleepSafe(1);
    }

    public boolean isButtonPlayExist() {
        hoverElement(playerFrame);
        return isElementPresence(buttonPlay,1);
    }

    public boolean isButtonPauseExist() {
        hoverElement(playerFrame);
        return isElementPresence(buttonPause,1);
    }

    public int getCurrentPage() {
        hoverElement(playerFrame);
        int pageNum = 0;
        if (!pageCurrent.getText().equals("")) {
            pageNum = parseInt(pageCurrent.getText());
        }
        System.out.println("CURRENT PAGE: " + pageNum);
        return pageNum;
    }

    public int getCurrentTiming() {
        hoverElement(playerFrame);
        int timeSec = parseInt(timingCurrent.getText().substring(6, 8));
        System.out.println("TIMER CURRENT: " + timingCurrent.getText());
        System.out.println("SECONDS: " + timeSec);
        return timeSec;
    }

    public float getCurrentSlider(){
        hoverElement(playerFrame);
        sliderCurrent = webDriver.findElement(sliderCurrentBy);
        float timeProg = parseFloat(sliderCurrent.getAttribute("aria-valuenow"));
        System.out.println("SLIDER%: " + timeProg);
        return timeProg;
    }

    public String getCurrentSubtitle(){
        String subTitle= webDriver.findElement(playerSubtitles).getAttribute("text");
        System.out.println("SUBTITLE: " + subTitle);
        return subTitle;
    }

    public int getVideoPlayerCurrentChunk(){
        String JsValue = executeJavaScript("return document.querySelector('video').played.length;");
        System.out.println("VideoPlayerCurrentChunk: " + JsValue);
        return parseInt(JsValue);
    }

    public float getVideoPlayerCurrentTime(){
        String JsValue = executeJavaScript("return document.querySelector('video').currentTime;");
        System.out.println("VideoPlayerCurrentTime: " + JsValue);
        return parseFloat(JsValue);
    }

    public int getNetworkTabChunkNumber(){
        String JsValue = executeJavaScript("const resources = performance.getEntriesByType('resource'); var out = []; " +
                "resources.forEach((entry) => {var n = entry.name; if (n.includes('demo_video')) { out.push(`${entry.name}`); }; }); return out.length;");
        System.out.println("NetworkTabChunkNumber: " + JsValue);
        return parseInt(JsValue);
    }

    public String getNetworkTabChunkLast(){
        String JsValue = executeJavaScript("const resources = performance.getEntriesByType('resource'); var out = []; " +
                "resources.forEach((entry) => {var n = entry.name; if (n.includes('demo_video')) { out.push(`${entry.name}`); }; }); return out[out.length - 1];");
        System.out.println("NetworkTabChunkLast: " + JsValue);
        return JsValue;
    }

    public void setVideoPlayerPaused(){
        executeJavaScript("document.querySelector('video').pause();");
    }

    public boolean ifVideoPlayerPaused(){
        return Boolean.parseBoolean(executeJavaScript("return document.querySelector('video').paused;"));
    }

    public void collectCurrentMetrics(){
        beforeTime = getCurrentTiming();
        beforeProgress = getCurrentSlider();
        beforePage = getCurrentPage();
        beforePlayerChunk = getVideoPlayerCurrentChunk();
        beforePlayerTime = getVideoPlayerCurrentTime();
        beforeNetworkChunkNumber = getNetworkTabChunkNumber();
        beforeNetworkChunkLast = getNetworkTabChunkLast();
    }

    public int getBeforeTime(){
        return beforeTime;
    }
    public float getBeforeProgress(){
        return beforeProgress;
    }
    public int getBeforePage(){
        return beforePage;
    }
    public int getBeforePlayerChunk(){
        return beforePlayerChunk;
    }
    public float getBeforePlayerTime(){
        return beforePlayerTime;
    }
    public int getBeforeNetworkChunkNumber(){
        return beforeNetworkChunkNumber;
    }
    public String getBeforeNetworkChunkLast(){
        return beforeNetworkChunkLast;
    }









}
