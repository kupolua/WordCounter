package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static info.deepidea.wordcounter.webForm.utils.Constants.BASE_URL;
import static info.deepidea.wordcounter.webForm.utils.Constants.WAIT_FOR_ELEMENT;
import static info.deepidea.wordcounter.webForm.utils.Util.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WordCloudFunctionalityTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String ELEMENT_SHOW_FILTER = "#showModalCloud > a";

    @BeforeClass
    public static void init() {
        final String localizationEn = "en";
        driver = getWebDriver(localizationEn);
        wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT);
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void wordCloudCanvas() {
        // given
        driver.get(BASE_URL);
        String link = "http://deepidea.info/wordcounter/testData/1500_words.pdf";

        // when
        putDataAndClickCountButton(driver, link);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        assertTrue(driver.findElement(By.id("canvas_cloud")).isDisplayed());
    }

    @Test
    public void wordCloudCanvas_badLink() {
        // given
        driver.get(BASE_URL);
        String link = "http://deepidea.info/wordcounter/testData/1500_words_bad.pdf";

        // when
        putDataAndClickCountButton(driver, link);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        assertFalse(driver.findElement(By.id("canvas_cloud")).isDisplayed());
    }

//    @Ignore
    @Test
    public void wordCloudCanvas_modalWindow() throws InterruptedException {
        // given
        final String elementIdWordCloud = "wordCloudModal";
        driver.get(BASE_URL);
        String link = "http://deepidea.info/wordcounter/testData/1500_words.pdf";

        // when
        putDataAndClickCountButton(driver, link);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);

        driver.findElement(By.cssSelector(ELEMENT_SHOW_FILTER)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementIdWordCloud)));

        //then
        assertTrue(driver.findElement(By.id("canvas_cloudModal")).isDisplayed());
    }

}

