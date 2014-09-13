package com.qalight.javacourse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WebFormTest {
    private static final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";
    private static final String CONTEXT = "/WordCounter/";
    private static final int TIME_WAIT = 3600;
    private static final int PORT = 8080;

    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:" + PORT + CONTEXT;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testEmptyUrlRequest() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("button")).click();
        sleep(TIME_WAIT);

        // then
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = "Request is null or empty";

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIncorrectUrl() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE + "a");
        driver.findElement(By.id("button")).click();
        sleep(TIME_WAIT);

        // then
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        final String expectedResult = "Can't connect to: http://defas.com.ua/java/pageForSeleniumTest.htmla";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSortingKeyAscending() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.className("sorting")).click();
        sleep(TIME_WAIT);

        //then
        String actualSortingKeyAscending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_SORTING_KEY_ASCENDING, actualSortingKeyAscending);
    }

    @Test
    public void testSortingValueAscending() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.className("sorting_desc")).click();
        sleep(TIME_WAIT);

        // then
        String actualSortingValueAscending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_SORTING_VALUE_ASCENDING, actualSortingValueAscending);
    }

    @Test
    public void testSortingKeyDescending() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.className("sorting")).click();
        driver.findElement(By.className("sorting")).click();
        sleep(TIME_WAIT);

        // then
        String actualSortingKeyDescending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_SORTING_KEY_DESCENDING, actualSortingKeyDescending);
    }

    @Test
    public void testSortingValueDescending() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        sleep(TIME_WAIT);

        // then
        String actualSortingValueDescending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_SORTING_VALUE_DESCENDING, actualSortingValueDescending);
    }

    @Test
    public void testSearchWord() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.cssSelector("input[type=\"search\"]")).clear();
        driver.findElement(By.cssSelector("input[type=\"search\"]")).sendKeys("білка");
        sleep(TIME_WAIT);

        // then
        String actualSearchWord = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_SEARCH_WORD, actualSearchWord);
    }

    @Test
    public void testNextResponse() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        sleep(TIME_WAIT);
        driver.findElement(By.linkText("Next")).click();

        // then
        String actualNextResponse = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_NEXT_RESPONSE, actualNextResponse);
    }

    @Test
    public void testPreviousResponse() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        sleep(TIME_WAIT);
        driver.findElement(By.linkText("Next")).click();
        driver.findElement(By.linkText("Previous")).click();


        // then
        String actualPreviousResponse = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_PREVIOUS_RESPONSE, actualPreviousResponse);
    }

    @Test
    public void testShowEntries() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        sleep(TIME_WAIT);
        new Select(driver.findElement(By.name("example_length"))).selectByVisibleText("25");

        // then
        String actualShowEntries = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_SHOW_ENTRIES, actualShowEntries);
    }

    @Test
    public void testInputText() throws Exception {
        // given
        driver.get(baseUrl);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(TEXT);
        driver.findElement(By.id("button")).click();
        sleep(TIME_WAIT);

        // then
        String actualInputText = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_INPUT_TEXT, actualInputText);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private static final String EXPECTED_INPUT_TEXT = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "one 4\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
            "объем 3\n" +
            "їжак 2\n" +
            "объём 1\n" +
            "дом 1\n" +
            "друг 1\n" +
            "єнот 1\n" +
            "Showing 1 to 10 of 18 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "Next";

    private static final String TEXT = "a One, the one ONE oNE  Two  two, two!@#$%^&*()_+=!123456789\n" +
            "\n" +
            "ёлка і Ёлка та ёлКА: ОБЪЁМ объем обЪем, але, но объем сказал завет человек время, имя, ученики, дом, друг, народ, слово, \n" +
            "\n" +
            "Їжак їжак єнот білка БІЛКА БіЛкА ";

    private static final String EXPECTED_SHOW_ENTRIES = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "one 4\n" +
            "two 3\n" +
            "білка 3\n" +
            "їжак 2\n" +
            "ёлка 2\n" +
            "объем 2\n" +
            "объём 1\n" +
            "дом 1\n" +
            "нообъем 1\n" +
            "єнот 1\n" +
            "время 1\n" +
            "vkamenniygmailcom 1\n" +
            "другнарод 1\n" +
            "человек 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
            "ученики 1\n" +
            "іёлка 1\n" +
            "завет 1\n" +
            "имя 1\n" +
            "слово 1\n" +
            "сказал 1\n" +
            "Showing 1 to 22 of 22 entries\n" +
            "Previous\n" +
            "1\n" +
            "Next";

    private static final String EXPECTED_PREVIOUS_RESPONSE = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "one 4\n" +
            "two 3\n" +
            "білка 3\n" +
            "їжак 2\n" +
            "ёлка 2\n" +
            "объем 2\n" +
            "объём 1\n" +
            "дом 1\n" +
            "нообъем 1\n" +
            "єнот 1\n" +
            "Showing 1 to 10 of 22 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "Next";

    private static final String EXPECTED_NEXT_RESPONSE = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "время 1\n" +
            "vkamenniygmailcom 1\n" +
            "другнарод 1\n" +
            "человек 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
            "ученики 1\n" +
            "іёлка 1\n" +
            "завет 1\n" +
            "имя 1\n" +
            "Showing 11 to 20 of 22 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "Next";

    private static final String EXPECTED_SEARCH_WORD = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "білка 3\n" +
            "Showing 1 to 1 of 1 entries (filtered from 22 total entries)\n" +
            "Previous\n" +
            "1\n" +
            "Next";

    private static final String EXPECTED_SORTING_KEY_ASCENDING = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
            "one 4\n" +
            "two 3\n" +
            "vkamenniygmailcom 1\n" +
            "білка 3\n" +
            "время 1\n" +
            "дом 1\n" +
            "другнарод 1\n" +
            "завет 1\n" +
            "Showing 1 to 10 of 22 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "Next";

    private static final String EXPECTED_SORTING_VALUE_ASCENDING = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "объём 1\n" +
            "дом 1\n" +
            "нообъем 1\n" +
            "єнот 1\n" +
            "время 1\n" +
            "vkamenniygmailcom 1\n" +
            "другнарод 1\n" +
            "человек 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
            "Showing 1 to 10 of 22 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "Next";

    private static final String EXPECTED_SORTING_KEY_DESCENDING = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
            "vkamenniygmailcom 1\n" +
            "время 1\n" +
            "дом 1\n" +
            "другнарод 1\n" +
            "завет 1\n" +
            "имя 1\n" +
            "нообъем 1\n" +
            "объём 1\n" +
            "Showing 1 to 10 of 22 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "Next";

    private static final String EXPECTED_SORTING_VALUE_DESCENDING = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "one 4\n" +
            "two 3\n" +
            "білка 3\n" +
            "їжак 2\n" +
            "ёлка 2\n" +
            "объем 2\n" +
            "объём 1\n" +
            "дом 1\n" +
            "нообъем 1\n" +
            "єнот 1\n" +
            "Showing 1 to 10 of 22 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "Next";
}