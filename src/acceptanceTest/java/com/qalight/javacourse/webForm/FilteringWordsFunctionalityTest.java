package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.qalight.javacourse.webForm.utils.Util.*;
import static com.qalight.javacourse.webForm.utils.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class FilteringWordsFunctionalityTest {
    private static WebDriver driver;

    private final String buttonIdFilteringWords = "buttonGetFilterWords";
    private final String elementShowFilter = "#showFilter > a";
    private final int waitTime = 5000;

    private @Value("${wordsEN}") String wordsEn;
    private @Value("${wordsRU}") String wordsRu;
    private @Value("${wordsUA}") String wordsUa;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    //todo: change Thread.sleep(WAIT_TIME) on better way
    @Test
    public void testWordFilterWithoutFilteringWords() throws Exception {
        // given
        driver.get(BASE_URL);
        String wordsForFilter = getWordsForFilter(wordsEn, wordsRu, wordsUa);
        final String expectedWordFilter = "marker 1";

        // when
        final String WORD_MARKER = "marker";
        putDataAndClickCountButton(driver, WORD_MARKER + SEPARATOR + wordsForFilter);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(buttonIdFilteringWords)).click();
        Thread.sleep(waitTime);

        // then
        String actualEnterTwoLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedWordFilter, actualEnterTwoLinks);
    }

    @Test
    public void testWordFilterWithFilteringWords() throws Exception {
        // given
        driver.get(BASE_URL);
        String wordsForFilter = getWordsForFilter(wordsEn, wordsRu, wordsUa);
        final String wordMarker = "marker";
        final String expectedWordFilter = "в 3\n" + "них 2\n" + "те 2\n" + "ж 2\n" + "з 2\n" + "й 2\n" +
                "к 2\n" + "то 2\n" + "м 2\n" + "н 2";
        final String buttonIdUnFilteringWords = "buttonGetUnFilterWords";

        // when
        putDataAndClickCountButton(driver, wordMarker + SEPARATOR + wordsForFilter);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(buttonIdFilteringWords)).click();
        Thread.sleep(waitTime);

        driver.findElement(By.id(buttonIdUnFilteringWords)).click();
        Thread.sleep(waitTime);

        // then
        String actualEnterTwoLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedWordFilter, actualEnterTwoLinks);
    }

    @Test
    public void testLinkShowFilter() throws Exception {
        // given
        driver.get(BASE_URL);
        final String idModalWindow = "simplemodal-placeholder";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(elementShowFilter)).click();
        boolean isModalWindow = driver.getPageSource().contains(idModalWindow);

        // then
        assertTrue(isModalWindow);
    }

    @Test
    public void testListFilteringWords() throws Exception {
        // given
        driver.get(BASE_URL);
        final String expectedResult = getWordsForFilter(wordsEn, wordsRu, wordsUa);
        final String elementIdFilteringWords = "wordsFilter";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(elementShowFilter)).click();
        Thread.sleep(waitTime);

        // then
        String actualResult = driver.findElement(By.id(elementIdFilteringWords)).getText();
        assertEquals(expectedResult, actualResult);
    }

    private final String getWordsForFilter(String... languages) {
        StringBuilder wordsForFilter = new StringBuilder();
        for (int i = 0; i < languages.length; i++) {
            wordsForFilter.append(languages[i]);
            if (i < (languages.length - 1)) {
                wordsForFilter.append(" ");
            }
        }
        return String.valueOf(wordsForFilter);
    }
}

