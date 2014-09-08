import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WebFormTest {
    private static final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";
    private static final String CONTEXT = "/WordCounter/";
    private static final int TIME_WAIT = 2000;
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
        driver.findElement(By.id("myButton")).click();
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
        driver.findElement(By.id("myButton")).click();
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
        driver.findElement(By.id("myButton")).click();
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
        driver.findElement(By.id("myButton")).click();
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
        driver.findElement(By.id("myButton")).click();
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
        driver.findElement(By.id("myButton")).click();
        sleep(TIME_WAIT);

        // then
        String actualSortingValueDescending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(EXPECTED_SORTING_VALUE_DESCENDING, actualSortingValueDescending);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private static final String EXPECTED_SORTING_KEY_ASCENDING = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "one 4\n" +
            "r 1\n" +
            "two 3\n" +
            "ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя 1\n" +
            "ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя 1\n" +
            "білка 3\n" +
            "объем 3\n" +
            "объём 1\n" +
            "ёлка 3\n" +
            "єнот 1\n" +
            "Showing 1 to 10 of 11 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
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
            "r 1\n" +
            "ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя 1\n" +
            "ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя 1\n" +
            "єнот 1\n" +
            "їжак 2\n" +
            "білка 3\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "объем 3\n" +
            "Showing 1 to 10 of 11 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "Next";

    private static final String EXPECTED_SORTING_KEY_DESCENDING = "Show\n" +
            "10\n" +
            "25\n" +
            "50\n" +
            "100\n" +
            "entries\n" +
            "Search:\n" +
            "Word Count\n" +
            "r 1\n" +
            "ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя 1\n" +
            "ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя 1\n" +
            "объём 1\n" +
            "єнот 1\n" +
            "їжак 2\n" +
            "two 3\n" +
            "білка 3\n" +
            "объем 3\n" +
            "ёлка 3\n" +
            "Showing 1 to 10 of 11 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
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
            "білка 3\n" +
            "ёлка 3\n" +
            "two 3\n" +
            "объем 3\n" +
            "їжак 2\n" +
            "объём 1\n" +
            "r 1\n" +
            "ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя 1\n" +
            "ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя 1\n" +
            "Showing 1 to 10 of 11 entries\n" +
            "Previous\n" +
            "1\n" +
            "2\n" +
            "Next";
}