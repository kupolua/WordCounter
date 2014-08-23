import com.qalight.javacourse.EntryPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WebFormTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";
    private final String expectedResultEmptyUrlRequest = "Your request is empty.";
    private final String expectedResultIncorrectUrl = "Your URL is malformed or not responding.";
    private final String expectedSortingKeyAscending = "http://defas.com.ua/java/pageForSeleniumTest.html\n" +
            "Word:\n" +
            "Count :\n" +
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
            "їжак 2";
    private final String expectedSortingValueAscending = "http://defas.com.ua/java/pageForSeleniumTest.html\n" +
            "Word:\n" +
            "Count :\n" +
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
            "one 4";
    private final String expectedSortingKeyDescending = "http://defas.com.ua/java/pageForSeleniumTest.html\n" +
            "Word:\n" +
            "Count :\n" +
            "їжак 2\n" +
            "єнот 1\n" +
            "ёлка 3\n" +
            "объём 1\n" +
            "объем 3\n" +
            "білка 3\n" +
            "ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя 1\n" +
            "ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя 1\n" +
            "two 3\n" +
            "r 1\n" +
            "one 4";
    private final String expectedSortingValueDescending = "http://defas.com.ua/java/pageForSeleniumTest.html\n" +
            "Word:\n" +
            "Count :\n" +
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
            "єнот 1";
    private int timeWait = 2000;
    private EntryPoint entryPoint = new EntryPoint();
    private int countPort = 0;
    private int jettyPort = EntryPoint.JETTY_PORT + 1;
    private final int COUNT_PORT_MAX = 10;

    @Before
    public void setUp() throws Exception {

        while (!availablePort(jettyPort) && countPort <= COUNT_PORT_MAX) {
            System.out.println("countPort" + countPort);
            if (countPort == COUNT_PORT_MAX) {
                throw new IndexOutOfBoundsException(countPort + " ports is bus!");
            }
            jettyPort++;
            countPort++;
        }
        entryPoint.jettyStart(jettyPort);
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:" + jettyPort + "/inputForm/UserHtmlFormLoaderServlet";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testEmptyUrlRequest() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedResultEmptyUrlRequest, actualResult);
    }


    @Test
    public void testIncorrectUrl() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys(HTML_TEST_PAGE + "a");
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedResultIncorrectUrl, actualResult);
    }

    @Test
    public void testSortingKeyAscending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualSortingKeyAscending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedSortingKeyAscending, actualSortingKeyAscending);
    }

    @Test
    public void testSortingValueAscending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.xpath("(//input[@name='userChoice'])[2]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualSortingValueAscending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedSortingValueAscending, actualSortingValueAscending);
    }

    @Test
    public void testSortingKeyDescending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.xpath("(//input[@name='userChoice'])[3]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualSortingKeyDescending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedSortingKeyDescending, actualSortingKeyDescending);
    }

    @Test
    public void testSortingValueDescending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.xpath("(//input[@name='userChoice'])[4]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualSortingValueDescending = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedSortingValueDescending, actualSortingValueDescending);
    }

    @After
    public void tearDown() throws Exception {

        entryPoint.jettyStop();
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean availablePort(int port) {

        Socket socket = null;
        try {
            socket = new Socket("localhost", port);
            return false;

        } catch (IOException e) {
            return true;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException("You should handle this error.", e);
                }
            }
        }
    }
}