package com.qalight.javacourse.webForm;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

import static com.qalight.javacourse.webForm.Util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CountingWordsFunctionalityTest {
    private WebDriver driver;

    public void setUp() throws Exception {
        if (isMacOs()) {
            driver = new SafariDriver();
        } else {
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);
    }

    @Test
    public void testDocumentExtensions() {
        // given
        final String TEST_PAGE_DOC = "";
        final String TEST_PAGE_DOCX = "";
        final String TEST_PAGE_HTM = "";
        final String TEST_PAGE_HTML = "http://defas.com.ua/java/pageForSeleniumTest.html";
        final String TEST_PAGE_ODP = "";
        final String TEST_PAGE_ODS = "";
        final String TEST_PAGE_ODT = "";
        final String TEST_PAGE_PPT = "";
        final String TEST_PAGE_PPTX = "";
        final String TEST_PAGE_XLS = "http://www.econ.yale.edu/~shiller/data/ie_data.xls";
        final String TEST_PAGE_XLSX = "";
        final String TEST_PAGE_TXT = "";
        final String TEST_PAGE_PDF = "http://defas.com.ua/java/textForSeleniumTest.pdf";

        driver.get(BASE_URL);

        assertEqualsExtension(TEST_PAGE_DOC);
        assertEqualsExtension(TEST_PAGE_DOCX);
        assertEqualsExtension(TEST_PAGE_HTM);
        assertEqualsExtension(TEST_PAGE_HTML);
        assertEqualsExtension(TEST_PAGE_ODP);
        assertEqualsExtension(TEST_PAGE_ODS);
        assertEqualsExtension(TEST_PAGE_ODT);
        assertEqualsExtension(TEST_PAGE_PPT);
        assertEqualsExtension(TEST_PAGE_PPTX);
        assertEqualsExtension(TEST_PAGE_XLS);
        assertEqualsExtension(TEST_PAGE_PPTX);
        assertEqualsExtension(TEST_PAGE_XLSX);
        assertEqualsExtension(TEST_PAGE_TXT);
        assertEqualsExtension(TEST_PAGE_PDF);
    }

    private void assertEqualsExtension(String testPage) {
        final String ANCHOR_HTML_PAGE_WITH_WORDS = "#countedWords > tbody";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(testPage);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = Util.waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        if (isReady) {
            String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
            final String EXPECTED_RESULT = "one 4\n" + "ёлка 3\n" + "two 3\n" + "білка 3\n" + "объем 3\n" + "їжак 2\n"
                    + "объём 1\n" + "ученики 1\n" + "і 1\n" + "але 1";
            assertEquals(EXPECTED_RESULT, actualResult);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }
}
