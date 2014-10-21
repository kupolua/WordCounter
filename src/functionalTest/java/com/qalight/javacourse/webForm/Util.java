package com.qalight.javacourse.webForm;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Util {
    public static final int WAIT_FOR_ELEMENT = 15;
    public static final int DEFAULT_WAIT_FOR_PAGE = 60;
    public static final int PORT = 8080;
    public static final String TEST_PAGE_HTML = "http://defas.com.ua/java/pageForSeleniumTest.html";
    public static final String CONTEXT = "/WordCounter/";
    public static final String BASE_URL = "http://localhost:" + PORT + CONTEXT;
    public static final String RESPONSE_IS_NOT_READY = "Verify Failed: Response is not ready";
    public static final String BUTTON_ID_COUNT_WORDS = "CountWords";
    public static final String ELEMENT_ID_TEXT_AREA = "textCount";

    public static boolean isMacOs() {
        boolean result = false;
        if (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) {
            result = true;
        }
        return result;
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
