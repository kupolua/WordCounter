package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            final String ELEMENT_CSS_INPUT_SEARCH = "input[type=\"search\"]";
            driver.findElement(By.cssSelector(ELEMENT_CSS_INPUT_SEARCH)).clear();
            final String SEARCH_WORD = "білка";
            driver.findElement(By.cssSelector(ELEMENT_CSS_INPUT_SEARCH)).sendKeys(SEARCH_WORD);

            String actualSearchWord = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            final String EXPECTED_SEARCH_WORD = "білка 3";
            assertEquals(EXPECTED_SEARCH_WORD, actualSearchWord);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }
}
