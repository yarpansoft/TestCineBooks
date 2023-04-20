package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PagePlayer;

public class TestPlayer extends TestBase {
    private final int typicalPlayTime = 2;
    private final int typicalPauseTime = 1;
    private final int typicalLagTime = 2;


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
        Assert.assertTrue(pagePlayer.getCurrentTiming() == pagePlayer.getBeforeTime(),"Time is changed");
        Assert.assertTrue(pagePlayer.getCurrentSlider() == pagePlayer.getBeforeProgress(),"ProgressBar position is changed");
        Assert.assertTrue(pagePlayer.getVideoPlayerCurrentChunk() == pagePlayer.getBeforePlayerChunk(),"Player. Another chunk in player");
        Assert.assertTrue(pagePlayer.getVideoPlayerCurrentTime() == pagePlayer.getBeforePlayerTime(),"Timeline is changed");

        pagePlayer.clickButtonPlay();
        Assert.assertTrue(pagePlayer.isButtonPauseExist() && ! pagePlayer.isButtonPlayExist());
        pagePlayer.collectCurrentMetrics();
        waitSafe(typicalPlayTime);
        Assert.assertTrue(pagePlayer.getCurrentTiming() >= pagePlayer.getBeforeTime() + typicalPlayTime,"New time is not greater than before");
        Assert.assertTrue(pagePlayer.getCurrentTiming() <= pagePlayer.getBeforeTime() + typicalPlayTime + typicalLagTime,"New time is much more greater than before");
        Assert.assertTrue(pagePlayer.getCurrentSlider() > pagePlayer.getBeforeProgress(),"ProgressBar position is not greater than before");
        Assert.assertTrue(pagePlayer.getVideoPlayerCurrentTime() > pagePlayer.getBeforePlayerTime(),"Timeline in Player is not increased");

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
        Assert.assertTrue(pagePlayer.getCurrentTiming() > pagePlayer.getBeforeTime(),"New time is not greater than before");
        Assert.assertTrue(pagePlayer.getCurrentSlider() > pagePlayer.getBeforeProgress(),"ProgressBar position is not greater than before");
        Assert.assertTrue(pagePlayer.getCurrentPage() == pagePlayer.getBeforePage() + 1,"Current page is not previous + 1");
        Assert.assertTrue(pagePlayer.getVideoPlayerCurrentChunk() == pagePlayer.getBeforePlayerChunk() + 1,"Player. New chunk is not play");
        Assert.assertTrue(pagePlayer.getVideoPlayerCurrentTime() > pagePlayer.getBeforePlayerTime(),"Timeline in Player is not increased");
        Assert.assertTrue(pagePlayer.getNetworkTabChunkNumber() == pagePlayer.getBeforeNetworkChunkNumber() + 1,"Network Tab. Chunk is not previous + 1");
        Assert.assertNotEquals(pagePlayer.getNetworkTabChunkLast(), pagePlayer.getBeforeNetworkChunkLast(),"Network Tab. Last chunk equals to previous");
    }

    @Test (priority = 3)
    public void test_03_BackwardButton(){
        //Arrange
        PagePlayer pagePlayer = new PagePlayer(webDriver);
        pagePlayer.openPlayer();
        pagePlayer.playVideo(typicalPlayTime);
        pagePlayer.clickButtonPageNext();
        pagePlayer.clickButtonPageNext();
        pagePlayer.collectCurrentMetrics();

        //Act
        pagePlayer.clickButtonPagePrev();

        //Assert
        Assert.assertTrue(pagePlayer.getCurrentTiming() < pagePlayer.getBeforeTime(),"New time is greater than before");
        Assert.assertTrue(pagePlayer.getCurrentSlider() < pagePlayer.getBeforeProgress(),"ProgressBar position is greater than before");
        Assert.assertEquals(pagePlayer.getCurrentPage(),pagePlayer.getBeforePage() - 1,  "Current page is not previous - 1");
        Assert.assertTrue(pagePlayer.getVideoPlayerCurrentChunk() <= pagePlayer.getBeforePlayerChunk(),"Player. Chunk No increased");
        Assert.assertTrue(pagePlayer.getVideoPlayerCurrentTime() < pagePlayer.getBeforePlayerTime(),"Timeline in Player is not decreased");
    }


}


