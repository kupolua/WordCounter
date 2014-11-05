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
        final String elementCssInputSearch = "input[type=\"search\"]";
        final String searchWord = "білка";
        final String expectedSearchWord = "білка 3";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(elementCssInputSearch)).clear();
        driver.findElement(By.cssSelector(elementCssInputSearch)).sendKeys(searchWord);

        // then
        String actualSearchWord = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedSearchWord, actualSearchWord);
    }
}
