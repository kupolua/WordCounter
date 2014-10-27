package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static com.qalight.javacourse.webForm.utils.Util.getWebDriver;
import static org.junit.Assert.assertEquals;
import static com.qalight.javacourse.webForm.utils.Constants.*;
import static com.qalight.javacourse.webForm.utils.Util.*;
import static org.junit.Assert.fail;

public class ResultsPresentationFunctionalityTest {
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
    public void testWordsByDefault() {
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
    public void testShowEntries25() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            final String ELEMENT_DATA_TABLES_LENGTH = "countedWords_length";
            final String DATA_TABLES_LENGTH_25 = "25";
            new Select(driver.findElement(By.name(ELEMENT_DATA_TABLES_LENGTH))).selectByVisibleText(DATA_TABLES_LENGTH_25);
            final String EXPECTED_SHOW_ENTRIES_25 =
                    "one 4\n" + "ёлка 3\n" + "two 3\n" + "білка 3\n" + "объем 3\n" + "їжак 2\n" + "объём 1\n" + "ученики 1\n" +
                    "і 1\n" + "але 1\n" + "имя 1\n" + "слово 1\n" + "a 1\n" + "но 1\n" + "дом 1\n" + "друг 1\n" + "єнот 1\n" +
                    "время 1\n" + "та 1\n" + "the 1\n" + "человек 1\n" + "народ 1\n" + "r 1\n" + "завет 1\n" + "сказал 1";
            String actualShowEntries = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_SHOW_ENTRIES_25, actualShowEntries);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }
}
