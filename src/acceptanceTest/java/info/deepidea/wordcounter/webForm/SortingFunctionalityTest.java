package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static info.deepidea.wordcounter.webForm.utils.Constants.*;
import static info.deepidea.wordcounter.webForm.utils.Util.*;
import static org.junit.Assert.assertEquals;

public class SortingFunctionalityTest {
    private final String elementIdSorting = "sorting";
    private static WebDriver driver;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void sortWords_byWordsDescending_text() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputText = "zebra ZEBRA automotive звон автомат";
        final String expectedResult = "звон 1\nавтомат 1\nzebra 2\nautomotive 1";

        // when
        putDataAndClickCountButton(driver, inputText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementIdSorting)).click();
        driver.findElement(By.cssSelector("th.sorting_asc")).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSorting_byWordsAscending_text() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputText = "zebra ZEBRA automotive звон автомат";
        final String expectedResult = "automotive 1\nzebra 2\nавтомат 1\nзвон 1";

        // when
        putDataAndClickCountButton(driver, inputText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementIdSorting)).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSorting_byQuantityDescending_docx() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputDocumentUrl = "http://deepidea.info/wordcounter/testData/test_sorting1.docx";
        final String expectedResult = "automotive 1\n" +
                "zebra 1\n" +
                "звон 1";

        // when
        putUrlDataAndClickCountButton(driver, inputDocumentUrl);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementIdSorting)).click();
        driver.findElement(By.className(elementIdSorting)).click();
        driver.findElement(By.className("sorting_asc")).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSorting_byQuantityDescending_text() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputText = "алабама, алфавит, АЛФАВИТ, а, а, а";
        final String expectedResult = "а 3\nалфавит 2\nалабама 1";

        // when
        putDataAndClickCountButton(driver, inputText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementIdSorting)).click();
        driver.findElement(By.className(elementIdSorting)).click();
        driver.findElement(By.className("sorting_asc")).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSorting_byQuantityAscending_docx() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputDocumentUrl = "http://deepidea.info/wordcounter/testData/test_sorting1.docx";
        final String expectedResult = "automotive 1\n" +
                "zebra 1\n" +
                "звон 1";

        // when
        putUrlDataAndClickCountButton(driver, inputDocumentUrl);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementIdSorting)).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSorting_byQuantityAscending_text() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputText = "алабама, алфавит, АЛФАВИТ, а, а, а";
        final String expectedResult = "алабама 1\nалфавит 2\nа 3";

        // when
        putDataAndClickCountButton(driver, inputText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className("sorting_desc")).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }
}