package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PagePlayer;

public class TestPlayer extends TestBase {
    private int typicalPlayTime = 2;
    private int typicalPauseTime = 1;
    private int typicalLagTime = 2;


    @Test (priority = 1)
    public void test_01_PlayPause() {
        //Arrange
        PagePlayer pagePlayer = new PagePlayer(webDriver);
        pagePlayer.openPlayer();
        pagePlayer.playVideo(typicalPlayTime);

        //Act - Assert
        Assert.assertTrue(pagePlayer.isButtonPauseExist() && ! pagePlayer.isButtonPlayExist());
        pagePlayer.clickButtonPause();
        Assert.assertTrue(pagePlayer.isButtonPlayExist() && ! pagePlayer.isButtonPauseExist());
        pagePlayer.collectCurrentMetrics();
        waitSafe(typicalPauseTime);
        Assert.assertTrue((pagePlayer.getCurrentTiming() == pagePlayer.getBeforeTime()),"Time is changed");
        Assert.assertTrue((pagePlayer.getCurrentSlider() == pagePlayer.getBeforeProgress()),"ProgressBar position is changed");

        pagePlayer.clickButtonPlay();
        Assert.assertTrue(pagePlayer.isButtonPauseExist() && ! pagePlayer.isButtonPlayExist());
        pagePlayer.collectCurrentMetrics();
        waitSafe(typicalPlayTime);
        Assert.assertTrue((pagePlayer.getCurrentTiming() >= pagePlayer.getBeforeTime() + typicalPlayTime),"New time is not greater than before");
        Assert.assertTrue((pagePlayer.getCurrentTiming() <= pagePlayer.getBeforeTime() + typicalPlayTime + typicalLagTime),"New time is much more greater than before");
        Assert.assertTrue((pagePlayer.getCurrentSlider() > pagePlayer.getBeforeProgress()),"ProgressBar position is not greater than before");
    }

    @Test (priority = 2)
    public void test_02_ForwardButton(){
        //Arrange
        PagePlayer pagePlayer = new PagePlayer(webDriver);
        pagePlayer.openPlayer();
        pagePlayer.playVideo(typicalPlayTime);
        pagePlayer.collectCurrentMetrics();

        //Act
        pagePlayer.clickButtonPageNext();

        //Assert
        Assert.assertTrue((pagePlayer.getCurrentTiming() > pagePlayer.getBeforeTime()),"New time is not greater than before");
        Assert.assertTrue((pagePlayer.getCurrentSlider() > pagePlayer.getBeforeProgress()),"ProgressBar position is not greater than before");
        Assert.assertTrue((pagePlayer.getCurrentPage() == pagePlayer.getBeforePage() + 1),"Current page is not previous + 1");
    }

    @Test (priority = 3)
    public void test_03_BackwardButton(){
        //Arrange
        PagePlayer pagePlayer = new PagePlayer(webDriver);
        pagePlayer.openPlayer();
        pagePlayer.playVideo(typicalPlayTime);
        pagePlayer.clickButtonPageNext();
        pagePlayer.collectCurrentMetrics();

        //Act
        pagePlayer.clickButtonPagePrev();

        //Assert
        Assert.assertTrue((pagePlayer.getCurrentTiming() < pagePlayer.getBeforeTime()),"New time is greater than before");
        Assert.assertTrue((pagePlayer.getCurrentSlider() < pagePlayer.getBeforeProgress()),"ProgressBar position is greater than before");
        Assert.assertTrue((pagePlayer.getCurrentPage() == pagePlayer.getBeforePage() - 1),"Current page is not previous - 1");
    }


}


