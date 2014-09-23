package com.qalight.javacourse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

// todo: сделать кодревью и рефакторинг: vkamenniy
// этот черновой вариант, но рабочий

public class WebFormTest {
    private static final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";
    private static final String PDF_TEST_PAGE = "http://defas.com.ua/java/textForSeleniumTest.pdf";
    private static final String CONTEXT = "/WordCounter/";
    private static final int WAIT_FOR_ELEMENT = 7;
    private static final int DEFAULT_WAIT_FOR_PAGE = 30;
    private static final int PORT = 8080;
    private static final String BASE_URL = "http://localhost:" + PORT + CONTEXT;
    private static final String RESPONSE_IS_NOT_READY = "Verify Failed: Response is not ready";
    private static final String ANCHOR_HTML_PAGE_WITH_WORDS = "tbody";
    private static final String ANCHOR_HTML_PAGE_WITHOUT_WORDS = "div#ajaxResponse";

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        if (isMacOs()){
            driver = new SafariDriver();
        } else {
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);
    }

    private boolean isMacOs() {
        boolean result = false;
        if (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) {
            result = true;
        }
        return result;
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testEmptyUrlRequest() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("button")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITHOUT_WORDS)).getText();
            String expectedResult = "Request is null or empty";
            assertEquals(expectedResult, actualResult);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testIncorrectUrl() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE + "a");
        driver.findElement(By.id("button")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITHOUT_WORDS)).getText();
            final String expectedResult = "Error during executing request: java.lang.RuntimeException: Can't connect to: http://defas.com.ua/java/pageForSeleniumTest.htmla";
            assertEquals(expectedResult, actualResult);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testSortingKeyAscending() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.className("sorting")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
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
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.className("sorting_desc")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
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
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.className("sorting")).click();
        driver.findElement(By.className("sorting")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
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
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualSortingValueDescending = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_SORTING_VALUE_DESCENDING, actualSortingValueDescending);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testSearchWord() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.cssSelector("input[type=\"search\"]")).clear();
        driver.findElement(By.cssSelector("input[type=\"search\"]")).sendKeys("білка");
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualSearchWord = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_SEARCH_WORD, actualSearchWord);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testNextResponse() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.linkText("Next")).click();

        // then
        if (isReady) {
            String actualNextResponse = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_NEXT_RESPONSE, actualNextResponse);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testPreviousResponse() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.linkText("Next")).click();
        driver.findElement(By.linkText("Previous")).click();

        // then
        if (isReady) {
            String actualPreviousResponse = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_PREVIOUS_RESPONSE, actualPreviousResponse);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testShowEntries() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        new Select(driver.findElement(By.name("example_length"))).selectByVisibleText("25");

        // then
        if (isReady) {
            String actualShowEntries = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_SHOW_ENTRIES, actualShowEntries);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testInputText() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(TEXT);
        driver.findElement(By.id("button")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualInputText = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_INPUT_TEXT, actualInputText);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testReadingPDF() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id("userUrlsList")).clear();
        driver.findElement(By.id("userUrlsList")).sendKeys(PDF_TEST_PAGE);
        driver.findElement(By.id("button")).click();
        driver.findElement(By.className("sorting")).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            String actualReadingPDF = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_READING_PDF, actualReadingPDF);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    public boolean waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds) {
        boolean jQcondition = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
                }
            });
            jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
            return jQcondition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jQcondition;
    }

    private static final String EXPECTED_READING_PDF =
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE 1\n" +
                    "vkamenniy@gmail.com 1\n" +
                    "dbdbddbaqschromeijljjsourc 1\n" +
                    "ddbcdpatterncompiledb 1\n" +
                    "eidchromeessmieutf 1\n" +
            "one 4\n" +
            "two 3\n" +
            "білка 3\n" +
                    "время 1";

    private static final String EXPECTED_INPUT_TEXT =
            "one 4\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
            "объем 3\n" +
            "їжак 2\n" +
            "объём 1\n" +
            "дом 1\n" +
            "друг 1\n" +
                    "єнот 1";

    private static final String TEXT = "a One, the one ONE oNE  Two  two, two!@#$%^&*()_+=!123456789\n" +
            "\n" +
            "ёлка і Ёлка та ёлКА: ОБЪЁМ объем обЪем, але, но объем сказал завет человек время, имя, ученики, дом, друг, народ, слово, \n" +
            "\n" +
            "Їжак їжак єнот білка БІЛКА БіЛкА ";

    private static final String EXPECTED_SHOW_ENTRIES =
            "one 4\n" +
                    "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
                    "объем 3\n" +
            "їжак 2\n" +
                    "vkamenniy@gmail.com 1\n" +
            "объём 1\n" +
            "дом 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
                    "друг 1\n" +
                    "єнот 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
                    "время 1\n" +
                    "человек 1\n" +
                    "народ 1\n" +
            "ученики 1\n" +
            "завет 1\n" +
            "имя 1\n" +
            "слово 1\n" +
                    "сказал 1";

    private static final String EXPECTED_PREVIOUS_RESPONSE =
            "one 4\n" +
                    "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
                    "объем 3\n" +
            "їжак 2\n" +
                    "vkamenniy@gmail.com 1\n" +
            "объём 1\n" +
            "дом 1\n" +
                    "http://habrahabr.ru/posts/top/weekly/ 1";

    private static final String EXPECTED_NEXT_RESPONSE =
            "друг 1\n" +
                    "єнот 1\n" +
                    "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
                    "время 1\n" +
                    "человек 1\n" +
                    "народ 1\n" +
                    "ученики 1\n" +
                    "завет 1\n" +
                    "имя 1\n" +
                    "слово 1";

    private static final String EXPECTED_SEARCH_WORD = "білка 3";

    private static final String EXPECTED_SORTING_KEY_ASCENDING =
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
                    "vkamenniy@gmail.com 1\n" +
            "one 4\n" +
            "two 3\n" +
            "білка 3\n" +
            "время 1\n" +
            "дом 1\n" +
                    "друг 1\n" +
                    "завет 1";

    private static final String EXPECTED_SORTING_VALUE_ASCENDING =
            "vkamenniy@gmail.com 1\n" +
            "объём 1\n" +
            "дом 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
                    "друг 1\n" +
                    "єнот 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
                    "время 1\n" +
                    "человек 1\n" +
                    "народ 1";

    private static final String EXPECTED_SORTING_KEY_DESCENDING =
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8 1\n" +
                    "vkamenniy@gmail.com 1\n" +
            "время 1\n" +
            "дом 1\n" +
                    "друг 1\n" +
            "завет 1\n" +
            "имя 1\n" +
                    "народ 1\n" +
                    "объём 1";

    private static final String EXPECTED_SORTING_VALUE_DESCENDING =
            "one 4\n" +
                    "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
                    "объем 3\n" +
            "їжак 2\n" +
                    "vkamenniy@gmail.com 1\n" +
            "объём 1\n" +
            "дом 1\n" +
                    "http://habrahabr.ru/posts/top/weekly/ 1";
}