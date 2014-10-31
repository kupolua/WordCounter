package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.utils.Constants.*;
import static com.qalight.javacourse.webForm.utils.Util.*;

import static org.junit.Assert.assertEquals;

public class CountingWordsFunctionalityTest {
    private static WebDriver driver;

    private final String pdfTestPage = "http://defas.com.ua/java/textForTest.pdf";
    private final String docTestPage = "http://defas.com.ua/java/textForTest.doc";

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void testInputText() {
        // given
        driver.get(BASE_URL);
        final String TEXT = "a One, the one ONE oNE  Two  two, two!@#$%^&*()_+=!123456789\n" + "https://www.google." +
                "com.ua/\n" + "http://" + "habrahabr.ru/posts/top/weekly/\n" + "vkamenniy@gmail.com\n" + "ёлка і Ёлка" +
                " та ёлКА: ОБЪЁМ объем обЪем, але," + " но объем сказал завет человек время, имя, ученики, дом, друг," +
                " " + "народ, слово, \n" + "Їжак їжак єнот білка " + "БІЛКА БіЛкА \n" + "R\n";

        // when
        putDataAndClickCountButton(driver, TEXT);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
    }

    @Test
    public void testEnterThreeLinks() {
        // given
        driver.get(BASE_URL);
        final String expectedEnterThreeLinks = "one 12\n" + "ёлка 9\n" + "two 9\n" + "білка 9\n" + "объем 9\n" +
                "їжак 6\n" + "объём 3\n" + "ученики 3\n" + "і 3\n" + "але 3";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE + SEPARATOR + pdfTestPage + SEPARATOR + docTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        String actualEnterThreeLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedEnterThreeLinks, actualEnterThreeLinks);
    }

    @Test
    public void testReadingHTML() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputHTML = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputHTML);
    }

    @Test
    public void testReadingHTM() {
        // given
        driver.get(BASE_URL);
        final String htmTestPage = "http://defas.com.ua/java/textForTest.htm";

        // when
        putDataAndClickCountButton(driver, htmTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputHTM = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputHTM);
    }

    @Test
    public void testReadingDOC() {
        // given
        driver.get(BASE_URL);
        final String docTestPage = "http://defas.com.ua/java/textForTest.doc";

        // when
        putDataAndClickCountButton(driver, docTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputDOC = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputDOC);
    }

    @Test
    public void testReadingDOCX() {
        // given
        driver.get(BASE_URL);
        final String docxTestPage = "http://defas.com.ua/java/textForTest.docx";

        // when
        putDataAndClickCountButton(driver, docxTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputDOCX = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputDOCX);
    }

    @Test
    public void testReadingODP() {
        // given
        driver.get(BASE_URL);
        final String odpTestPage = "http://defas.com.ua/java/textForTest.odp";

        // when
        putDataAndClickCountButton(driver, odpTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputODP = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputODP);
    }
//before this test we need fix bug in next sprint
//    @Test
    public void testReadingODS() {
        // given
        driver.get(BASE_URL);
        final String odsTestPage = "http://defas.com.ua/java/textForTest.ods";

        // when
        putDataAndClickCountButton(driver, odsTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputODS = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputODS);
    }

    @Test
    public void testReadingODT() {
        // given
        driver.get(BASE_URL);
        final String odtTestPage = "http://defas.com.ua/java/textForTest.odt";

        // when
        putDataAndClickCountButton(driver, odtTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputODT = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputODT);
    }

    @Test
    public void testReadingPDF() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, pdfTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputPDF = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputPDF);
    }

    @Test
    public void testReadingPPT() {
        // given
        driver.get(BASE_URL);
        final String pptTestPage = "http://defas.com.ua/java/textForTest.ppt";

        // when
        putDataAndClickCountButton(driver, pptTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputPPT = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputPPT);
    }

    @Test
    public void testReadingPPTX() {
        // given
        driver.get(BASE_URL);
        final String pptxTestPage = "http://defas.com.ua/java/textForTest.pptx";

        // when
        putDataAndClickCountButton(driver, pptxTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputPPTX = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputPPTX);
    }

    @Test
    public void testReadingRTF() {
        // given
        driver.get(BASE_URL);
        final String rtfTestPage = "http://defas.com.ua/java/textForTest.rtf";

        // when
        putDataAndClickCountButton(driver, rtfTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputRTF = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputRTF);
    }

    @Test
    public void testReadingTXT() {
        // given
        driver.get(BASE_URL);
        final String txtTestPage = "http://defas.com.ua/java/textForTest.txt";

        // when
        putDataAndClickCountButton(driver, txtTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputTXT = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputTXT);
    }

    //before this test we need fix bug in next sprint
    //    @Test
    public void testReadingXLS() {
        // given
        driver.get(BASE_URL);
        final String xlsTestPage = "http://defas.com.ua/java/textForTest.xls";

        // when
        putDataAndClickCountButton(driver, xlsTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputXLS = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputXLS);
    }

    //before this test we need fix bug in next sprint
    //    @Test
    public void testReadingXLSX() {
        // given
        driver.get(BASE_URL);
        final String xlsxTestPage = "http://defas.com.ua/java/textForTest.xlsx";

        // when
        putDataAndClickCountButton(driver, xlsxTestPage);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualInputXLSX = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualInputXLSX);
    }
}
