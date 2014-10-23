package com.qalight.javacourse.webForm.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import static com.qalight.javacourse.webForm.util.Constants.*;

public class Util {

    public static WebDriver startWebDriver() {
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);
        return driver;
    }

    public static boolean waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds) {
        final String JQUERY_ACTIVE = "return jQuery.active == 0";
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
