package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static com.qalight.javacourse.webForm.utils.Constants.*;
import static com.qalight.javacourse.webForm.utils.Util.*;

import static org.junit.Assert.assertEquals;

public class DifferentFunctionalityTest {
    private static WebDriver driver;

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
        String expectedResult = "Request is null or empty";

        // when
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.id(ELEMENT_ID_MESSAGE)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIncorrectUrl() {
        // given
        driver.get(BASE_URL);
        final String INCORRECT_SYMBOL = "a";
        final String expectedResult = "Error during executing request: Can't connect to: " +
                "http://defas.com.ua/java/textForTest.htmla";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE + INCORRECT_SYMBOL);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.id(ELEMENT_ID_MESSAGE)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testImproperInput() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "Error during executing request: " +
                "System cannot count entered text {kris@gmail.com www.google.com %/*\\^# 0}. " +
                "Did you forget to add 'http://' to the link or entered not readable text?";

        // when
        putDataAndClickCountButton(driver, IMPROPER_INPUT);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.id(ELEMENT_ID_MESSAGE)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testNextResponse() {
        // given
        driver.get(BASE_URL);
        final String expectedNextResponse = "имя 1\n" + "слово 1\n" + "a 1\n" + "но 1\n" + "дом 1\n" + "друг 1\n" +
                "єнот 1\n" + "время 1\n" + "та 1\n" + "the 1";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();

        // then
        String actualNextResponse = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedNextResponse, actualNextResponse);
    }

    @Test
    public void testPreviousResponse() {
        // given
        driver.get(BASE_URL);
        final String ELEMENT_ID_LINK_PREV = "countedWords_previous";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();
        driver.findElement(By.id(ELEMENT_ID_LINK_PREV)).click();

        // then
        String actualPreviousResponse = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualPreviousResponse);
    }

    @Test
    public void testParallelExecution() throws Exception {
        // given
        WebDriver driverSecondary = new FirefoxDriver();
        driverSecondary.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);

        driver.get(BASE_URL);
        driverSecondary.get(BASE_URL);
        //todo change link
        final String htmlTestPageSecondary = "https://dl.dropboxusercontent.com/u/12495182/" +
                "textForSeleniumTestSecondary.pdf";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);

        driverSecondary.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driverSecondary.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(htmlTestPageSecondary);

        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
        driverSecondary.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        waitForJQueryProcessing(driverSecondary, WAIT_FOR_ELEMENT);

        driverSecondary.quit();

        // then
        String actualParallelExecution = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualParallelExecution);
    }

    @Test
    public void testEnterTwoLinksOneByOne() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualEnterTwoLinksOneByOne = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_STANDARD_RESULT, actualEnterTwoLinksOneByOne);
    }
}
