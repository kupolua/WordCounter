package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static info.deepidea.wordcounter.webForm.utils.Constants.*;
import static info.deepidea.wordcounter.webForm.utils.Util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StatisticWordsFunctionalityTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

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
    public void statisticWordsPlainText_conditionOne() {
        // given
        driver.get(BASE_URL);
        final String text = "аэросъемка, АЭРОСЪЕМКА, дЫмаРь, дымарь";
        final String expectedResult = "Name Amount\n" +
                "Total Words\n" +
                "4\n" +
                "Unique Words\n" +
                "2\n" +
                "Total Characters\n" +
                "38\n" +
                "Total Characters(without spaces)\n" +
                "35";

        // when
        putDataAndClickCountButton(driver, text);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.id(STATISTIC_TABLE)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEnterThreeLinks() {
        // given
        final String request = "http://deepidea.info/wordcounter/testData/page_latin.html " +
                "http://deepidea.info/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx " +
                "http://deepidea.info/wordcounter/testData/letters%2Bnumbers.txt";
        final String expectedEnterThreeLinks = "test 3\n" +
                "думи 2\n" +
                "a 1\n" +
                "мої 1\n" +
                "santa-monica 1\n" +
                "people 1\n" +
                "nice 1Name Amount\n" +
                "Total Words\n" +
                "10\n" +
                "Unique Words\n" +
                "7\n" +
                "Total Characters\n" +
                "71\n" +
                "Total Characters(without spaces)\n" +
                "57";

        driver.get(BASE_URL);

        // when
        putUrlDataAndClickCountButton(driver, request);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        String actualResult = driver.findElement(By.id(STATISTIC_TABLE)).getText();
        String actualEnterThreeLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedEnterThreeLinks, actualEnterThreeLinks+actualResult);
    }

    @Test
    public void statisticWordInWebPageViaUrl_invalidLink() {
        // given
        driver.get(BASE_URL);
        String invalidLink = "http://kupol...in.ua/wordcounter/testData/no_text.html";
        String expectedResult = "Cannot connect to the source:" +
                " >http://kupol...in.ua/wordcounter/testData/no_text.html";

        // when
        putUrlDataAndClickCountButton(driver, invalidLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.className(elementCssErrorSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        //then
        String actualResult = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();
        assertEquals(expectedResult, actualResult);
        assertFalse(driver.findElement(By.id(STATISTIC_TABLE)).isDisplayed());
    }

    @Test
    public void testEnterThreeLinks_withBrokenHtmlLink() throws Exception {
        // given
        final String request = "http://kupol....in.ua/wordcounter/testData/test_page_latin.html " +
                "http://deepidea.info/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx " +
                "http://deepidea.info/wordcounter/testData/letters%2Bnumbers.txt";
        final String expectedEnterThreeLinks = "думи 2\n" + "мої 1\n" + "people 1\n" + "nice 1";
        String expectedErrorMassage = "Cannot connect to the source: >http://kupol....in.ua/wordcounter/testData/" +
                "test_page_latin.html";
        String expectedStatisticResult = "Name Amount\n" +
                "Total Words\n" +
                "5\n" +
                "Unique Words\n" +
                "4\n" +
                "Total Characters\n" +
                "37\n" +
                "Total Characters(without spaces)\n" +
                "30";

        driver.get(BASE_URL);

        // when
        putUrlDataAndClickCountButton(driver, request);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);

        String actualStatisticResult = driver.findElement(By.id(STATISTIC_TABLE)).getText();
        String actualEnterThreeLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        driver.findElement(By.className(elementCssErrorSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        //then
        String actualErrorMassage = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();

        assertEquals(expectedEnterThreeLinks, actualEnterThreeLinks);
        assertEquals(expectedErrorMassage, actualErrorMassage);
        assertEquals(expectedStatisticResult, actualStatisticResult);
    }

    @Test
    public void testParallelExecution() throws Exception {
        // given
        WebDriver driverSecondary = new FirefoxDriver();
        driverSecondary.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);

        driver.get(BASE_URL);
        driverSecondary.get(BASE_URL);

        final String pageLatinLink = "http://deepidea.info/wordcounter/testData/page_latin.html";
        final String htmlTestPageSecondary =
                "http://deepidea.info/wordcounter/testData/textForSeleniumTestSecondary.pdf";
        final String expectedResult = "test 3\n" +
                "a 1\n" +
                "santa-monica 1Name Amount\n" +
                "Total Words\n" +
                "5\n" +
                "Unique Words\n" +
                "3\n" +
                "Total Characters\n" +
                "34\n" +
                "Total Characters(without spaces)\n" +
                "27";

        // when
        driver.findElement(By.id(ELEMENT_ID_URL_TAB)).click();
        driver.findElement(By.id(ELEMENT_ID_URL_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_URL_TEXT_AREA)).sendKeys(pageLatinLink);

        driverSecondary.findElement(By.id(ELEMENT_ID_URL_TAB)).click();
        driverSecondary.findElement(By.id(ELEMENT_ID_URL_TEXT_AREA)).clear();
        driverSecondary.findElement(By.id(ELEMENT_ID_URL_TEXT_AREA)).sendKeys(htmlTestPageSecondary);

        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
        driverSecondary.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        waitForJQueryProcessing(driverSecondary, WAIT_FOR_ELEMENT);

        driverSecondary.quit();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        String actualStatisticResult = driver.findElement(By.id(STATISTIC_TABLE)).getText();
        assertEquals(expectedResult, actualResult+actualStatisticResult);
    }
}

