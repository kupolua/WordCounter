package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

import static info.deepidea.wordcounter.webForm.utils.Constants.*;
import static info.deepidea.wordcounter.webForm.utils.Util.*;

public class SearchingFunctionalityTest {
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
    public void testSearchWord() throws Exception {
        // given
        driver.get(BASE_URL);
        final String htmlPageCyrillic = "http://deepidea.info/wordcounter/testData/page_cyrillic.html";
        final String elementCssInputSearch = "input[type=\"search\"]";
        final String searchWord = "нет";
        final String expectedSearchWord = "нет 1";

        // when
        putUrlDataAndClickCountButton(driver, htmlPageCyrillic);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(elementCssInputSearch)).clear();
        driver.findElement(By.cssSelector(elementCssInputSearch)).sendKeys(searchWord);

        // then
        driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        String actualSearchWord = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSearchWord, actualSearchWord);
    }

    @Test
    public void testSearchWord_noSuchWord() throws Exception {
        // given
        driver.get(BASE_URL);
        final String htmlPageLatin = "http://deepidea.info/wordcounter/testData/page_latin.html";
        final String elementCssInputSearch = "input[type=\"search\"]";
        final String searchWord = "yet";
        final String expectedSearchWord = "No matching records found";

        // when
        putDataAndClickCountButton(driver, htmlPageLatin);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(elementCssInputSearch)).clear();
        driver.findElement(By.cssSelector(elementCssInputSearch)).sendKeys(searchWord);

        // then
        String actualSearchWord = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSearchWord, actualSearchWord);
    }

    @Test
    public void testSearchWord_putNumber() throws Exception {
        // given
        driver.get(BASE_URL);
        final String odtLink = "http://deepidea.info/wordcounter/testData/all_lang.odt";
        final String elementCssInputSearch = "input[type=\"search\"]";
        final String searchWord = "2";
        final String expectedSearchWord = "аэросъемка 2\n" + "sweet 2\n" + "дымарь 2";

        // when
        putUrlDataAndClickCountButton(driver, odtLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(elementCssInputSearch)).clear();
        driver.findElement(By.cssSelector(elementCssInputSearch)).sendKeys(searchWord);

        // then
        String actualSearchWord = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSearchWord, actualSearchWord);
    }
}