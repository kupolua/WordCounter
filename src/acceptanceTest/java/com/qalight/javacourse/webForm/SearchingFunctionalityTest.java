package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

import static com.qalight.javacourse.webForm.utils.Constants.*;
import static com.qalight.javacourse.webForm.utils.Util.*;

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
        final String htmlPageCyrillic = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";
        final String elementCssInputSearch = "input[type=\"search\"]";
        final String searchWord = "нет";
        final String expectedSearchWord = "нет 1";

        // when
        putDataAndClickCountButton(driver, htmlPageCyrillic);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(elementCssInputSearch)).clear();
        driver.findElement(By.cssSelector(elementCssInputSearch)).sendKeys(searchWord);

        // then
        String actualSearchWord = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSearchWord, actualSearchWord);
    }

    @Test
    public void testSearchWord_noSuchWord() throws Exception {
        // given
        driver.get(BASE_URL);
        final String htmlPageLatin = "http://kupol.in.ua/wordcounter/testData/page_latin.html";
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
        final String odtLink = "http://kupol.in.ua/wordcounter/testData/all_lang.odt";
        final String elementCssInputSearch = "input[type=\"search\"]";
        final String searchWord = "2";
        final String expectedSearchWord = "аэросъемка 2\n" + "sweet 2\n" + "дымарь 2";

        // when
        putDataAndClickCountButton(driver, odtLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(elementCssInputSearch)).clear();
        driver.findElement(By.cssSelector(elementCssInputSearch)).sendKeys(searchWord);

        // then
        String actualSearchWord = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSearchWord, actualSearchWord);
    }
}
