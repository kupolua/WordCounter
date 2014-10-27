package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

import static com.qalight.javacourse.webForm.utils.Constants.*;
import static com.qalight.javacourse.webForm.utils.Util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DifferentFunctionalityTest {
    private static WebDriver driver;
    private static WebDriver driverSecondary;

    private final String ELEMENT_ID_LINK_NEXT = "countedWords_next";
    private final String ELEMENT_ID_MESSAGE = "messageCounter";

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void testEmptyUrlRequest() {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String expectedResult = "WordCounter Exception: Request is null or empty";
            String actualResult = driver.findElement(By.id(ELEMENT_ID_MESSAGE)).getText();
            assertEquals(expectedResult, actualResult);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testIncorrectUrl() {
        // given
        driver.get(BASE_URL);

        // when
        final String INCORRECT_SYMBOL = "a";
        putDataAndClickCountButton(driver, HTML_TEST_PAGE + INCORRECT_SYMBOL);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            final String expectedResult = "WordCounter Exception: Error during executing request: Can't connect to: " +
                    "http://defas.com.ua/java/textForTest.htmla";
            String actualResult = driver.findElement(By.id(ELEMENT_ID_MESSAGE)).getText();
            assertEquals(expectedResult, actualResult);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testNextResponse() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();
            final String EXPECTED_NEXT_RESPONSE = "имя 1\n" + "слово 1\n" + "a 1\n" + "но 1\n" + "дом 1\n" + "друг 1\n" +
                    "єнот 1\n" + "время 1\n" + "та 1\n" + "the 1";
            String actualNextResponse = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_NEXT_RESPONSE, actualNextResponse);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testPreviousResponse() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            final String ELEMENT_ID_LINK_PREV = "countedWords_previous";
            driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();
            driver.findElement(By.id(ELEMENT_ID_LINK_PREV)).click();
            String actualPreviousResponse = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualPreviousResponse);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testParallelExecution() throws Exception {
        // given
        driverSecondary = new FirefoxDriver();
        driverSecondary.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);

        driver.get(BASE_URL);
        driverSecondary.get(BASE_URL);
        //todo change link
        final String HTML_TEST_PAGE_SECONDARY = "https://dl.dropboxusercontent.com/u/12495182/textForSeleniumTestSecondary.pdf";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);

        driverSecondary.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driverSecondary.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE_SECONDARY);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
        driverSecondary.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        boolean isReadySecondary = waitForJQueryProcessing(driverSecondary, WAIT_FOR_ELEMENT);

        driverSecondary.quit();

        //then
        if (isReady && isReadySecondary) {
            String actualParallelExecution = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_STANDARD_RESULT, actualParallelExecution);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }


    @Test
    public void testEnterTwoLinksOneByOne() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            putDataAndClickCountButton(driver, HTML_TEST_PAGE);
            driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

            isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

            if (isReady) {
                String actualEnterTwoLinksOneByOne = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS))
                        .getText();
                assertEquals(EXPECTED_STANDARD_RESULT, actualEnterTwoLinksOneByOne);
            } else {
                fail(RESPONSE_IS_NOT_READY);
            }
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

}
