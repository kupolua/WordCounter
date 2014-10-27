package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.utils.Constants.*;
import static com.qalight.javacourse.webForm.utils.Util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CountingWordsFunctionalityTest {
    private static WebDriver driver;
    private final String PDF_TEST_PAGE = "http://defas.com.ua/java/textForTest.pdf";
    private final String DOC_TEST_PAGE = "http://defas.com.ua/java/textForTest.doc";

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
        final String TEXT =
                "a One, the one ONE oNE  Two  two, two!@#$%^&*()_+=!123456789\n" + "https://www.google.com.ua/\n" + "http://" +
                "habrahabr.ru/posts/top/weekly/\n" + "vkamenniy@gmail.com\n" + "ёлка і Ёлка та ёлКА: ОБЪЁМ объем обЪем, але," +
                " но объем сказал завет человек время, имя, ученики, дом, друг, " + "народ, слово, \n" + "Їжак їжак єнот білка " +
                "БІЛКА БіЛкА \n" + "R\n";

        // when
        putDataAndClickCountButton(driver, TEXT);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testEnterThreeLinks() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE + SEPARATOR + PDF_TEST_PAGE + SEPARATOR + DOC_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            final String EXPECTED_ENTER_THREE_LINKS = "one 12\n" + "ёлка 9\n" + "two 9\n" + "білка 9\n" + "объем 9\n" +
                    "їжак 6\n" + "объём 3\n" + "ученики 3\n" + "і 3\n" + "але 3";
            String actualEnterThreeLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_ENTER_THREE_LINKS, actualEnterThreeLinks);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingHTML() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingHTM() {
        // given
        driver.get(BASE_URL);
        final String HTM_TEST_PAGE = "http://defas.com.ua/java/textForTest.htm";

        // when
        putDataAndClickCountButton(driver, HTM_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingDOC() {
        // given
        driver.get(BASE_URL);
        final String DOC_TEST_PAGE = "http://defas.com.ua/java/textForTest.doc";

        // when
        putDataAndClickCountButton(driver, DOC_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingDOCX() {
        // given
        driver.get(BASE_URL);
        final String DOCX_TEST_PAGE = "http://defas.com.ua/java/textForTest.docx";

        // when
        putDataAndClickCountButton(driver, DOCX_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingODP() {
        // given
        driver.get(BASE_URL);
        final String ODP_TEST_PAGE = "http://defas.com.ua/java/textForTest.odp";

        // when
        putDataAndClickCountButton(driver, ODP_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

//    @Test
    public void testReadingODS() {
        // given
        driver.get(BASE_URL);
        final String ODS_TEST_PAGE = "http://defas.com.ua/java/textForTest.ods";

        // when
        putDataAndClickCountButton(driver, ODS_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingODT() {
        // given
        driver.get(BASE_URL);
        final String ODT_TEST_PAGE = "http://defas.com.ua/java/textForTest.odt";

        // when
        putDataAndClickCountButton(driver, ODT_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingPDF() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, PDF_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingPPT() {
        // given
        driver.get(BASE_URL);
        final String PPT_TEST_PAGE = "http://defas.com.ua/java/textForTest.ppt";

        // when
        putDataAndClickCountButton(driver, PPT_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingPPTX() {
        // given
        driver.get(BASE_URL);
        final String PPTX_TEST_PAGE = "http://defas.com.ua/java/textForTest.pptx";

        // when
        putDataAndClickCountButton(driver, PPTX_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingRTF() {
        // given
        driver.get(BASE_URL);
        final String RTF_TEST_PAGE = "http://defas.com.ua/java/textForTest.rtf";

        // when
        putDataAndClickCountButton(driver, RTF_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingTXT() {
        // given
        driver.get(BASE_URL);
        final String TXT_TEST_PAGE = "http://defas.com.ua/java/textForTest.txt";

        // when
        putDataAndClickCountButton(driver, TXT_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

//    @Test
    public void testReadingXLS() {
        // given
        driver.get(BASE_URL);
        final String XLS_TEST_PAGE = "http://defas.com.ua/java/textForTest.xls";

        // when
        putDataAndClickCountButton(driver, XLS_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

//    @Test
    public void testReadingXLSX() {
        // given
        driver.get(BASE_URL);
        final String XLSX_TEST_PAGE = "http://defas.com.ua/java/textForTest.xlsx";

        // when
        putDataAndClickCountButton(driver, XLSX_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }
}
