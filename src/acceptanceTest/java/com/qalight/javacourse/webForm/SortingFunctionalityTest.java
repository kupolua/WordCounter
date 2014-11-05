package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.utils.Constants.*;
import static com.qalight.javacourse.webForm.utils.Util.*;
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
    public void testSortingKeyAscending() throws Exception {
        // given
        driver.get(BASE_URL);
        final String expectedSortingKeyAscending = "a 1\n" + "one 4\n" + "r 1\n" + "the 1\n" + "two 3\n" +
                "але 1\n" + "білка 3\n" + "время 1\n" + "дом 1\n" + "друг 1";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.className(elementIdSorting)).click();

        // then
        String actualSortingKeyAscending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSortingKeyAscending, actualSortingKeyAscending);
    }

    @Test
    public void testSortingValueAscending() throws Exception {
        // given
        driver.get(BASE_URL);
        final String elementIdSortingDesc = "sorting_desc";
        final String expectedSortingValueAscending = "объём 1\n" + "ученики 1\n" + "і 1\n" + "але 1\n" +
                "имя 1\n" + "слово 1\n" + "a 1\n" + "но 1\n" + "дом 1\n" + "друг 1";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.className(elementIdSortingDesc)).click();

        // then
        String actualSortingValueAscending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSortingValueAscending, actualSortingValueAscending);
    }

    @Test
    public void testSortingKeyDescending() throws Exception {
        // given
        driver.get(BASE_URL);
        final String elementIdSortingAsc = "sorting_asc";
        final String expectedSortingKeyDescending = "їжак 2\n" + "і 1\n" + "єнот 1\n" + "ёлка 3\n" + "человек 1\n" +
                "ученики 1\n" + "та 1\n" + "слово 1\n" + "сказал 1\n" + "объём 1";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.className(elementIdSorting)).click();
        driver.findElement(By.className(elementIdSortingAsc)).click();

        // then
        String actualSortingKeyDescending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSortingKeyDescending, actualSortingKeyDescending);
    }

    @Test
    public void testSortingValueDescending() throws Exception {
        // given
        driver.get(BASE_URL);
        final String expectedSortingValueDescending = "one 4\n" + "ёлка 3\n" + "two 3\n" + "білка 3\n" + "объем 3\n" +
                "їжак 2\n" + "объём 1\n" + "ученики 1\n" + "і 1\n" + "але 1";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualSortingValueDescending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSortingValueDescending, actualSortingValueDescending);
    }
}
