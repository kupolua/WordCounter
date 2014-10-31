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

public class SortingFunctionalityTest {
    private static final String ELEMENT_ID_SORTING = "sorting";
    private static final String ELEMENT_ID_SORTING_ASC = "sorting_asc";
    private static final String ELEMENT_ID_SORTING_DESC = "sorting_desc";
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

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        // todo: Replace '//' to '// '. See https://google-styleguide.googlecode.com/svn/trunk/javaguide.html#s4.6.2-horizontal-whitespace
        //then
        // todo: Actions must be performed in when
        if (isReady) {
            driver.findElement(By.className(ELEMENT_ID_SORTING)).click();
            final String EXPECTED_SORTING_KEY_ASCENDING = "a 1\n" + "one 4\n" + "r 1\n" + "the 1\n" + "two 3\n" +
                    "але 1\n" + "білка 3\n" + "время 1\n" + "дом 1\n" + "друг 1";
            String actualSortingKeyAscending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_SORTING_KEY_ASCENDING, actualSortingKeyAscending);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testSortingValueAscending() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        // todo: Actions must be performed in when
        if (isReady) {
            driver.findElement(By.className(ELEMENT_ID_SORTING_DESC)).click();
            final String EXPECTED_SORTING_VALUE_ASCENDING = "объём 1\n" + "ученики 1\n" + "і 1\n" + "але 1\n" +
                    "имя 1\n" + "слово 1\n" + "a 1\n" + "но 1\n" + "дом 1\n" + "друг 1";
            String actualSortingValueAscending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_SORTING_VALUE_ASCENDING, actualSortingValueAscending);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testSortingKeyDescending() throws Exception {
        // given
        driver.get(BASE_URL);
        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        // todo: Actions must be performed in when
        if (isReady) {
            driver.findElement(By.className(ELEMENT_ID_SORTING)).click();
            driver.findElement(By.className(ELEMENT_ID_SORTING_ASC)).click();
            final String EXPECTED_SORTING_KEY_DESCENDING = "їжак 2\n" + "і 1\n" + "єнот 1\n" + "ёлка 3\n" + "человек 1\n" +
                    "ученики 1\n" + "та 1\n" + "слово 1\n" + "сказал 1\n" + "объём 1";
            String actualSortingKeyDescending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_SORTING_KEY_DESCENDING, actualSortingKeyDescending);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testSortingValueDescending() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            final String EXPECTED_SORTING_VALUE_DESCENDING = "one 4\n" + "ёлка 3\n" + "two 3\n" + "білка 3\n" + "объем 3\n" +
                    "їжак 2\n" + "объём 1\n" + "ученики 1\n" + "і 1\n" + "але 1";
            String actualSortingValueDescending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_SORTING_VALUE_DESCENDING, actualSortingValueDescending);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }
}
