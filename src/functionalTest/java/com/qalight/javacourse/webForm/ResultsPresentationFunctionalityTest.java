package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.util.Util.getWebDriver;

public class ResultsPresentationFunctionalityTest {
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
