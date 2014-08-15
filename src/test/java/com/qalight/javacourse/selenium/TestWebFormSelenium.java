package com.qalight.javacourse.selenium;

import com.qalight.javacourse.EntryPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Vova on 08.08.2014.
 */

// todo: all tests should end with word *Test TestWebSelenium

public class TestWebFormSelenium {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private final String expectedResultEmptyUrlRequest = "";
    private final String expectedResultUrlContainHttps = "";
    private final String expectedResultIncorrectUrl = "";
    private int timeWait = 2000;

    @Before
    public void setUp() throws Exception {

        EntryPoint.jettyStart();
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
        assertEquals(expectedResultEmptyUrlRequest, actualResult);
        EntryPoint.jettyStop();
    }

    @Test
    public void testUrlContainHttps() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("https://easypay.ua/");
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedResultUrlContainHttps, actualResult);
        EntryPoint.jettyStop();
    }

    @Ignore
    @Test
    public void testIncorrectUrl() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://htmlbook.u/html/input");
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedResultIncorrectUrl, actualResult);
        EntryPoint.jettyStop();
    }

    @Ignore
    @Test
    public void testSortingKeyAscending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/");
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\SortingKeyAscending.txt")));
        assertEquals(expectedResult, actualResult);
    }

    @Ignore
    @Test
    public void testSortingValueAscending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/");
        driver.findElement(By.xpath("(//input[@name='userCheck'])[2]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\SortingValueAscending.txt")));
        assertEquals(expectedResult, actualResult);
    }

    @Ignore
    @Test
    public void testSortingKeyDescending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/");
        driver.findElement(By.xpath("(//input[@name='userCheck'])[3]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\SortingKeyDescending.txt")));
        assertEquals(expectedResult, actualResult);
    }

    @Ignore
    @Test
    public void testSortingValueDescending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/");
        driver.findElement(By.xpath("(//input[@name='userCheck'])[4]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\SortingValueDescending.txt")));
        assertEquals(expectedResult, actualResult);
    }

    @Ignore
    @Test
    public void testConsolidatedResult() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/, " +
                "http://www.httpunit.org/doc/developers.html");
        driver.findElement(By.xpath("(//input[@name='typeStatisticResult'])[2]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait * 2);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\ConsolidatedResult.txt")));
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
