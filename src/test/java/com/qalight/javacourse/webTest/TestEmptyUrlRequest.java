package com.qalight.javacourse.webTest;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import com.qalight.javacourse.EntryPoint;
import org.junit.*;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Vova on 08.08.2014.
 */

public class TestEmptyUrlRequest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String expectedResult = "HTTP ERROR: 500\n" +
            "Problem accessing /inputForm/UserRequestHandlerServlet. Reason:\n" +
            "    protocol = http host = null\n" +
            "Powered by Jetty://";
    private int timeWait = 2000;

    @Before
    public void setUp() throws Exception {
        new EntryPoint().jettyStart();
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8021/inputForm/UserHtmlFormLoaderServlet";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testEmptyUrlRequest() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedResult, actualResult);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
