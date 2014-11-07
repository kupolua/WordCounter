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
        driver.get(BASE_URL);
        final String elementIdAboutUs = "aboutUsLink";
        final String elementIdAboutUsHead = "aboutUsHead";

        // when
        driver.findElement(By.id(elementIdAboutUs)).click();

        // then
        boolean isAboutDisplayed = driver.findElement(By.id(elementIdAboutUsHead)).isDisplayed();
        assertTrue(isAboutDisplayed);
    }
}
