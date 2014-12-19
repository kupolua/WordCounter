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

public class CountingWordsFunctionalityTest {
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
    public void countWordInWebPageViaUrl_cyrillic() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "аэросъемка 2\nдымарь 2\nнет 1\nщеголь 1\nон 1";
        final String request = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";

        // when
        putDataAndClickCountButton(driver, request);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordsPlainText_conditionOne() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "аэросъемка 2\nдымарь 2";
        final String text = "аэросъемка, АЭРОСЪЕМКА, дЫмаРь, дымарь";

        // when
        putDataAndClickCountButton(driver, text);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordsPlainText_conditionTwo() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "під'їзд 2\nй 1\nєнот 1\nґедзь 1";
        final String text = "Під'їзд, ПІД'ЇЗД, ґедзь, єнот, й";

        // when
        putDataAndClickCountButton(driver, text);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordsPlainText_conditionThree() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "sweet 2\nlady 1\nloving 1\nwife 1";
        final String text = "SWEET sweet lady loving wife";

        // when
        putDataAndClickCountButton(driver, text);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordsPlainText_conditionFour() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "mad 1\nмін 1\nрыжий 1";
        final String text = "рыжий%%%%%% 148MAD мін@";

        // when
        putDataAndClickCountButton(driver, text);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordInWebPageViaUrl_latin() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "test 3\na 1\nsanta-monica 1";

        // when
        putDataAndClickCountButton(driver, "http://kupol.in.ua/wordcounter/testData/page_latin.html");
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordInWebPageViaUrl_noTextOnPage() {
        // given
        driver.get(BASE_URL);
        final String noTextOnPage = "http://kupol.in.ua/wordcounter/testData/no_text.html";
        String expectedResult = "System cannot count entered text. Did you forget to add 'http://' to the link or" +
                " entered not readable text?";

        // when
        putDataAndClickCountButton(driver, noTextOnPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.className(elementCssSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        //then
        String actualResult = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordInWebPageViaUrl_invalidLink() {
        // given
        driver.get(BASE_URL);
        String invalidLink = "http://kupol...in.ua/wordcounter/testData/no_text.html";
        String expectedResult = "Cannot connect to the source:" +
                " >http://kupol...in.ua/wordcounter/testData/no_text.html";

        // when
        putDataAndClickCountButton(driver, invalidLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.className(elementCssSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        //then
        String actualResult = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordInDocumentViaUrl_cyrillic_Pptx() {
        // given
        driver.get(BASE_URL);
        String pptxLink = "http://kupol.in.ua/wordcounter/testData/" +
                "%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx";
        String expectedResult = "думи 2\nмої 1";

        // when
        putDataAndClickCountButton(driver, pptxLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordInDocumentViaUrl_lettersAndNumbers_Txt() {
        // given
        driver.get(BASE_URL);
        String txtLink = "http://kupol.in.ua/wordcounter/testData/letters+numbers.txt";
        String expectedResult = "people 1\nnice 1";

        // when
        putDataAndClickCountButton(driver, txtLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordInDocumentViaUrl_1500Words_Pdf() {
        // given
        driver.get(BASE_URL);
        String pdfLink = "https://dl.dropboxusercontent.com/u/18408157/Test_data/1500_words.pdf";
        String expectedResult = "word 665\nтексте 507\nмінімум 253\nв 75";

        // when
        putDataAndClickCountButton(driver, pdfLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void countWordInDocumentViaUrl_noTextInFile_Pdf() {
        // given
        driver.get(BASE_URL);
        String noTextInPdf = "http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf";
        String expectedResult = "System cannot count text in the source as it is empty or contains non-readable" +
                " content or symbols: >http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf";

        // when
        putDataAndClickCountButton(driver, noTextInPdf);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.className(elementCssSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        //then
        String actualResult = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEnterThreeLinks() {
        // given
        final String request = "http://kupol.in.ua/wordcounter/testData/page_latin.html " +
                "http://kupol.in.ua/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx " +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String expectedEnterThreeLinks = "test 3\n" + "думи 2\n" + "a 1\n" + "мої 1\n" + "santa-monica 1\n" +
                "people 1\n" + "nice 1";

        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, request);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        String actualEnterThreeLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedEnterThreeLinks, actualEnterThreeLinks);
    }

    @Test
    public void testEnterThreeLinks_withBrokenHtmlLink() throws Exception {
        // given
        final String request = "http://kupol....in.ua/wordcounter/testData/test_page_latin.html " +
                "http://kupol.in.ua/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx " +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String expectedEnterThreeLinks = "думи 2\n" + "мої 1\n" + "people 1\n" + "nice 1";
        String expectedErrorMassage = "Cannot connect to the source: >http://kupol....in.ua/wordcounter/testData/" +
                "test_page_latin.html";

        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, request);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.className(elementCssSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        //then
        String actualEnterThreeLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        String actualErrorMassage = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();

        assertEquals(expectedEnterThreeLinks, actualEnterThreeLinks);
        assertEquals(expectedErrorMassage, actualErrorMassage);
    }

    @Test
    public void testEnterThreeLinks_withNoReadableTextInPdf() throws Exception {
        // given
        final String request = "http://kupol.in.ua/wordcounter/testData/page_latin.html " +
                "http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf " +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers.txt";
        final String expectedEnterThreeLinks = "test 3\n" + "a 1\n" + "santa-monica 1\n" + "people 1\n" + "nice 1";
        String expectedErrorMassage = "System cannot count text in the source as it is empty or contains non-readable" +
                " content or symbols: >http://kupol.in.ua/wordcounter/testData/Pdf_no_text.pdf";

        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, request);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.className(elementCssSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        //then
        String actualEnterThreeLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        String actualErrorMassage = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();

        assertEquals(expectedEnterThreeLinks, actualEnterThreeLinks);
        assertEquals(expectedErrorMassage, actualErrorMassage);
    }

    @Test
    public void testEnterThreeLinks_withBrokenTxtLink() {
        // given
        final String request = "http://kupol.in.ua/wordcounter/testData/test_page_latin.html " +
                "http://kupol.in.ua/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx " +
                "http://kupol.in.ua/wordcounter/testData/letters%2Bnumbers...txt";
        final String expectedEnterThreeLinks = "думи 2\n" + "мої 1";

        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, request);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        String actualEnterThreeLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedEnterThreeLinks, actualEnterThreeLinks);
    }

    @Test
    public void testParallelExecution() throws Exception {
        // given
        WebDriver driverSecondary = new FirefoxDriver();
        driverSecondary.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);

        driver.get(BASE_URL);
        driverSecondary.get(BASE_URL);

        final String pageLatinLink = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
        final String htmlTestPageSecondary =
                "http://kupol.in.ua/wordcounter/testData/textForSeleniumTestSecondary.pdf";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(pageLatinLink);

        driverSecondary.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driverSecondary.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(htmlTestPageSecondary);

        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
        driverSecondary.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        waitForJQueryProcessing(driverSecondary, WAIT_FOR_ELEMENT);

        driverSecondary.quit();

        // then
        final String expectedResult = "test 3\n" + "a 1\n" + "santa-monica 1";
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }
}
