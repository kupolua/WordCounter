package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.utils.Constants.BASE_URL;
import static com.qalight.javacourse.webForm.utils.Util.getWebDriver;
import static org.junit.Assert.assertTrue;

public class InformationAboutFunctionalityTest {
    private static WebDriver driver;
    private final int waitTime = 3000;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void openAboutProjectPage_fromMenuTab() throws InterruptedException {
        // given
        driver.get(BASE_URL);
        final String elementIdAboutUs = "aboutUsLink";
        final String elementIdAboutUsText = "aboutText";

        // when
        driver.findElement(By.id(elementIdAboutUs)).click();
        Thread.sleep(waitTime);

        // then
        boolean isAboutDisplayed = driver.findElement(By.id(elementIdAboutUsText)).isDisplayed();
        assertTrue(isAboutDisplayed);
    }

    @Ignore
    @Test
    public void openAboutProjectPage_fromLinkInText() throws InterruptedException {
        // given
        driver.get(BASE_URL);
        final String elementIdAboutUsText = "aboutText";
        String aboutUsLink = "find out more...";

        // when
        driver.findElement(By.linkText(aboutUsLink)).click();
        Thread.sleep(waitTime);

        // then
        boolean isAboutTextDisplayed = driver.findElement(By.id(elementIdAboutUsText)).isDisplayed();
        assertTrue(isAboutTextDisplayed);
    }
}
