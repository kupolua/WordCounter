package com.qalight.javacourse.webForm.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static com.qalight.javacourse.webForm.utils.Constants.*;

public class Util {
    private static WebDriver driver;

    public static WebDriver getWebDriver() {
        driver = new SafariDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);
        return driver;
    }

    public static void putDataAndClickCountButton(WebDriver driver, String data) {
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(data);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();
    }

    public static boolean waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds) {
        final String jqueryActive = "return jQuery.active == 0";
        boolean jQcondition = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            new WebDriverWait(driver, timeOutInSeconds) {
                // todo: replace this with lambda expression
            }.until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject).executeScript(jqueryActive);
                }
            });
            jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript(jqueryActive);
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);
            return jQcondition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jQcondition;
    }
}
