package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.util.Util.*;

public class FilteringWordsFunctionalityTest {
    private static WebDriver driver;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }
}

