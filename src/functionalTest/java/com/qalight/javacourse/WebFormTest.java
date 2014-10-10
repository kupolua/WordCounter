package com.qalight.javacourse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class WebFormTest {
    private static final int WAIT_FOR_ELEMENT = 15;
    private static final int DEFAULT_WAIT_FOR_PAGE = 60;
    private static final int PORT = 8080;
    private static final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";
    private static final String HTML_TEST_PAGE_SECONDARY = "https://dl.dropboxusercontent.com/u/12495182/" +
            "textForSeleniumTestSecondary.pdf";
    private static final String INCORRECT_SYMBOL = "a";
    private static final String EXPECTED_EMPTY_RESULT = "";
    private static final String EXPECTED_INCORRECT_RESULT = "";
    private static final String PDF_TEST_PAGE = "http://defas.com.ua/java/textForSeleniumTest.pdf";
    private static final String URL_SEPARATOR = " ";
    private static final String CONTEXT = "/WordCounter/";
    private static final String BASE_URL = "http://localhost:" + PORT + CONTEXT;
    private static final String RESPONSE_IS_NOT_READY = "Verify Failed: Response is not ready";
    private static final String ANCHOR_HTML_PAGE_WITH_WORDS = "#countedWords > tbody";
    private static final String ANCHOR_HTML_PAGE_WITHOUT_WORDS = "div#wordCounterResponse";
    private static final String BUTTON_ID_COUNT_WORDS = "CountWords";
    private static final String BUTTON_ID_FILTER_WORDS = "buttonGetFilterWords";
    private static final String ELEMENT_ID_TEXT_AREA = "textCount";
    private static final String ELEMENT_ID_SORTING = "sorting";
    private static final String ELEMENT_ID_SORTING_ASC = "sorting_asc";
    private static final String ELEMENT_ID_SORTING_DESC = "sorting_desc";
    private static final String ELEMENT_CSS_INPUT_SEARCH = "input[type=\"search\"]";
    private static final String SEARCH_WORD = "білка";
    private static final String ELEMENT_ID_LINK_NEXT = "countedWords_next";
    private static final String ELEMENT_ID_LINK_PREV = "countedWords_previous";
    private static final String ELEMENT_DATATABLES_LENGTH = "countedWords_length";
    private static final String DATATABLES_LENGTH = "25";
    private static final String WORD_MARKER = "marker";
    private static final String ELEMENT_SHOW_FILTER = "#showFilter > a";
    private static final String IS_MODAL_WINDOW = "simplemodal-placeholder";
    private static final String ELEMENT_ID_ABOUT_US = "aboutUsLink";
    private static final String IS_PAGE_ABOUT_US = "allAboutContent";
    private static final String JQUERY_ACTIVE = "return jQuery.active == 0";
    private static final String EXPECTED_PARALLEL_EXECUTION = "one 4\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
            "объем 3\n" +
            "їжак 2\n" +
            "объём 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "ученики 1\n" +
            "имя 1";
    private static final String EXPECTED_ENTER_TWO_LINKS = "one 8\n" +
            "ёлка 6\n" +
            "two 6\n" +
            "білка 6\n" +
            "объем 6\n" +
            "їжак 4\n" +
            "объём 2\n" +
            "http://habrahabr.ru/posts/top/weekly/ 2\n" +
            "ученики 2\n" +
            "имя 2";
    private static final String EXPECTED_READING_PDF = "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE 1\n" +
            "vkamenniy@gmail.com 1\n" +
            "dbdbddbaqschromeijljjsourc 1\n" +
            "ddbcdpatterncompiledb 1\n" +
            "eidchromeessmieutf 1\n" +
            "one 4\n" +
            "two 3\n" +
            "білка 3\n" +
            "время 1";
    private static final String EXPECTED_INPUT_TEXT = "one 4\n" +
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
            "ёлка і Ёлка та ёлКА: ОБЪЁМ объем обЪем, але, но объем сказал завет человек время, имя, ученики, дом, " +
            "друг, народ, слово, \n" +
            "\n" +
            "Їжак їжак єнот білка БІЛКА БіЛкА ";
    private static final String EXPECTED_SHOW_ENTRIES = "one 4\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
            "объем 3\n" +
            "їжак 2\n" +
            "объём 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "ученики 1\n" +
            "имя 1\n" +
            "слово 1\n" +
            "vkamenniy@gmail.com 1\n" +
            "дом 1\n" +
            "друг 1\n" +
            "єнот 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern." +
            "compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_" +
            "sm=93&ie=UTF-8 1\n" +
            "время 1\n" +
            "человек 1\n" +
            "народ 1\n" +
            "завет 1\n" +
            "сказал 1";
    private static final String EXPECTED_PREVIOUS_RESPONSE = "one 4\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
            "объем 3\n" +
            "їжак 2\n" +
            "объём 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "ученики 1\n" +
            "имя 1";
    private static final String EXPECTED_NEXT_RESPONSE = "one 4\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
            "объем 3\n" +
            "їжак 2\n" +
            "объём 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "ученики 1\n" +
            "имя 1";
    private static final String EXPECTED_SEARCH_WORD = "білка 3";
    private static final String EXPECTED_SORTING_KEY_ASCENDING = "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern." +
            "compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome" +
            "&es_sm=93&ie=UTF-8 1\n" +
            "vkamenniy@gmail.com 1\n" +
            "one 4\n" +
            "two 3\n" +
            "білка 3\n" +
            "время 1\n" +
            "дом 1\n" +
            "друг 1\n" +
            "завет 1";
    private static final String EXPECTED_SORTING_VALUE_ASCENDING = "объём 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "ученики 1\n" +
            "имя 1\n" +
            "слово 1\n" +
            "vkamenniy@gmail.com 1\n" +
            "дом 1\n" +
            "друг 1\n" +
            "єнот 1\n" +
            "https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+" +
            "Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=" +
            "chrome&es_sm=93&ie=UTF-8 1";
    private static final String EXPECTED_SORTING_KEY_DESCENDING = "їжак 2\n" +
            "єнот 1\n" +
            "ёлка 3\n" +
            "человек 1\n" +
            "ученики 1\n" +
            "слово 1\n" +
            "сказал 1\n" +
            "объём 1\n" +
            "объем 3\n" +
            "народ 1";
    private static final String EXPECTED_SORTING_VALUE_DESCENDING = "one 4\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "білка 3\n" +
            "объем 3\n" +
            "їжак 2\n" +
            "объём 1\n" +
            "http://habrahabr.ru/posts/top/weekly/ 1\n" +
            "ученики 1\n" +
            "имя 1";
    private static final String EXPECTED_WORD_FILTER = "marker 1";

    private
    @Value("${wordsEN}")
    String WORDS_EN;
    private
    @Value("${wordsRU}")
    String WORDS_RU;
    private
    @Value("${wordsUA}")
    String WORDS_UA;
    private WebDriver driver;
    private WebDriver driverSecondary;

    @Before
    public void setUp() throws Exception {
        if (isMacOs()) {
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
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITHOUT_WORDS)).getText();
            String expectedResult = EXPECTED_EMPTY_RESULT;
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE + INCORRECT_SYMBOL);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITHOUT_WORDS)).getText();
            final String expectedResult = EXPECTED_INCORRECT_RESULT;
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            driver.findElement(By.className(ELEMENT_ID_SORTING)).click();
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            driver.findElement(By.className(ELEMENT_ID_SORTING_DESC)).click();
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            driver.findElement(By.className(ELEMENT_ID_SORTING)).click();
            driver.findElement(By.className(ELEMENT_ID_SORTING_ASC)).click();
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.cssSelector(ELEMENT_CSS_INPUT_SEARCH)).clear();
            driver.findElement(By.cssSelector(ELEMENT_CSS_INPUT_SEARCH)).sendKeys(SEARCH_WORD);
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();
            driver.findElement(By.id(ELEMENT_ID_LINK_PREV)).click();

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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            new Select(driver.findElement(By.name(ELEMENT_DATATABLES_LENGTH))).selectByVisibleText(DATATABLES_LENGTH);
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(TEXT);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
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
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(PDF_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            driver.findElement(By.className(ELEMENT_ID_SORTING)).click();
            String actualReadingPDF = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_READING_PDF, actualReadingPDF);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testEnterTwoLinks() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE + URL_SEPARATOR + PDF_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            String actualEnterTwoLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_ENTER_TWO_LINKS, actualEnterTwoLinks);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testWordFilter() throws Exception {
        // given
        String wordsForFilter = getWordsForFilter(WORDS_EN, WORDS_RU, WORDS_UA);
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(WORD_MARKER + URL_SEPARATOR + wordsForFilter);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            String actualEnterTwoLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_WORD_FILTER, actualEnterTwoLinks);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testParallelExecution() throws Exception {
        // given
        if (isMacOs()) {
            driverSecondary = new SafariDriver();
        } else {
            driverSecondary = new FirefoxDriver();
        }
        driverSecondary.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);

        driver.get(BASE_URL);
        driverSecondary.get(BASE_URL);

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
            driver.findElement(By.id(BUTTON_ID_FILTER_WORDS)).click();
            String actualParallelExecution = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_PARALLEL_EXECUTION, actualParallelExecution);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testLinkShowFilter() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            driver.findElement(By.cssSelector(ELEMENT_SHOW_FILTER)).click();
            boolean isModalWindow = driver.getPageSource().contains(IS_MODAL_WINDOW);
            assertTrue(isModalWindow);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testLinkAboutUs() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            driver.findElement(By.cssSelector(ELEMENT_SHOW_FILTER)).click();
            boolean isModalWindow = driver.getPageSource().contains(IS_MODAL_WINDOW);
            assertTrue(isModalWindow);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    private final String getWordsForFilter(String... languages) {
        StringBuilder wordsForFilter = new StringBuilder();
        for (String language : languages) {
            wordsForFilter.append(language);
            wordsForFilter.append(" ");
        }
        return wordsForFilter.toString();
    }

    public boolean waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds) {
        boolean jQcondition = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject).executeScript(JQUERY_ACTIVE);
                }
            });
            jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript(JQUERY_ACTIVE);
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);
            return jQcondition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jQcondition;
    }
}