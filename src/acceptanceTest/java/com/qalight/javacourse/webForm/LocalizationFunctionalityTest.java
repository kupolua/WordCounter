package com.qalight.javacourse.webForm;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qalight.javacourse.webForm.utils.Constants.BASE_URL;
import static com.qalight.javacourse.webForm.utils.Util.getWebDriver;
import static org.junit.Assert.assertEquals;

public class LocalizationFunctionalityTest {
    private WebDriver driver;
    private String idStringAtWebForm = "p1";

    @Test
    public void testEnglishLocal() {
        // given
        final String localizationEn = "en";
        final String expectedResult = "Get the most frequently used words in one click!";
        driver = getWebDriver(localizationEn);

        // when
        driver.get(BASE_URL);

        // then
        String actualResult = driver.findElement(By.id(idStringAtWebForm)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRussianLocal() {
        // given
        final String localizationRu = "ru";
        final String expectedResult = "Получите список наиболее часто используемых слов в один миг!";
        driver = getWebDriver(localizationRu);

        // when
        driver.get(BASE_URL);

        // then
        String actualResult = driver.findElement(By.id(idStringAtWebForm)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testUkraineLocal() {
        // given
        final String localizationUk = "uk";
        final String expectedResult = "Отримайте перелік слів, що найчастіше зустрічаються у тексті, всього за одну" +
                " мить!";
        driver = getWebDriver(localizationUk);

        // when
        driver.get(BASE_URL);

        // then
        String actualResult = driver.findElement(By.id(idStringAtWebForm)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGermanyLocal() {
        // given
        final String localizationDe = "de";
        final String expectedResult = "Get the most frequently used words in one click!";
        driver = getWebDriver(localizationDe);

        // when
        driver.get(BASE_URL);

        // then
        String actualResult = driver.findElement(By.id(idStringAtWebForm)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }
}

