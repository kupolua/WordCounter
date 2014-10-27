package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.utils.Util.getWebDriver;
import static com.qalight.javacourse.webForm.utils.Constants.*;
import static org.junit.Assert.assertTrue;

public class InformationAboutFunctionalityTest {
    private static WebDriver driver;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void testLinkAboutUs() {
        // given
        final String elementIdAboutUs = "aboutUsLink";
        driver.get(BASE_URL);

        // when
        driver.findElement(By.id(elementIdAboutUs)).click();

        //then
        boolean isAboutDisplayed = driver.findElement(By.id("aboutUsHead")).isDisplayed();
        assertTrue(isAboutDisplayed);
    }

}
