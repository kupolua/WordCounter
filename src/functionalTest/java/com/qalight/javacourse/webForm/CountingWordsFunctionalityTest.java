package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.util.Constants.*;
import static com.qalight.javacourse.webForm.util.Util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CountingWordsFunctionalityTest {
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
    public void testInputText() throws Exception {
        // given
        driver.get(BASE_URL);

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
}
