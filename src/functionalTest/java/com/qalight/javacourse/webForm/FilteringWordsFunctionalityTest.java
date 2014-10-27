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
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class FilteringWordsFunctionalityTest {
    private static WebDriver driver;

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

    @Test
    public void testWordFilter() throws Exception {
        // given
        String wordsForFilter = getWordsForFilter(wordsEn, wordsRu, wordsUa);
        driver.get(BASE_URL);

        // when
        final String WORD_MARKER = "marker";
        putDataAndClickCountButton(driver, WORD_MARKER + SEPARATOR + wordsForFilter);


        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            final String BUTTON_ID_FILTERING_WORDS = "buttonGetFilterWords";
            driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
            final int WAIT_TIME = 2000;
            Thread.sleep(WAIT_TIME);
            final String EXPECTED_WORD_FILTER = "marker 1";
            String actualEnterTwoLinks = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            assertEquals(EXPECTED_WORD_FILTER, actualEnterTwoLinks);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    @Test
    public void testLinkShowFilter() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            final String ELEMENT_SHOW_FILTER = "#showFilter > a";
            final String ID_MODAL_WINDOW = "simplemodal-placeholder";
            driver.findElement(By.cssSelector(ELEMENT_SHOW_FILTER)).click();
            boolean isModalWindow = driver.getPageSource().contains(ID_MODAL_WINDOW);
            assertTrue(isModalWindow);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }


    private final String getWordsForFilter(String... languages) {
        StringBuilder wordsForFilter = new StringBuilder();
        for (String language : languages) {
            wordsForFilter.append(language);
            wordsForFilter.append(" ");
        }
        return String.valueOf(wordsForFilter);
    }

}

