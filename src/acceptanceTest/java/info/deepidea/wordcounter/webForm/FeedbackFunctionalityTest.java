package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static info.deepidea.wordcounter.webForm.utils.Constants.BASE_URL;
import static info.deepidea.wordcounter.webForm.utils.Constants.WAIT_FOR_ELEMENT;
import static info.deepidea.wordcounter.webForm.utils.Util.getWebDriver;

public class FeedbackFunctionalityTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String ELEMENT_SHOW_FILTER = ".sendFeedback";

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

    @Ignore
    @Test //todo complete feedback Test after choice feedback system
    public void feedback() {
        // given
        final String elementIdWordCloud = "sendFeedback";
        driver.get(BASE_URL);
        String link = "http://deepidea.info/wordcounter/testData/1500_words.pdf";

        // when

        //then
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ELEMENT_SHOW_FILTER)));
    }

}