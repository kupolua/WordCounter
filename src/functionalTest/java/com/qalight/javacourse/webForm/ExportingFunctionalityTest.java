package com.qalight.javacourse.webForm;

import org.apache.tika.Tika;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static com.qalight.javacourse.webForm.utils.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static com.qalight.javacourse.webForm.utils.Util.*;

public class ExportingFunctionalityTest {
    private static WebDriver driver;
    private static Tika documentConverter;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
        documentConverter = new Tika();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    // todo: This test don't executing
//    @Test
    public void testExportPdf() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        // todo: Actions must be performed in when
        if (isReady) {
            driver.findElement(By.id(BUTTON_PDF)).click();
//            checkAlert();
            // todo: Use Code -> Reformat code in all classes
           Thread.sleep(2000);

            final String EXPECTED_PDF = "expectedPdf.pdf";
            File expectedPdfPath = new File(PATH_RESOURCES + EXPECTED_PDF);
            String expectedPdf = documentConverter.parseToString(expectedPdfPath);

            final String ACTUAL_PDF = "calculatedWords.pdf";
            File actualPdfPath = new File(PATH_RESOURCES + ACTUAL_PDF);
            String actualPdf = documentConverter.parseToString(actualPdfPath);
            actualPdfPath.delete();

            assertEquals(expectedPdf, actualPdf);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    // todo: This test don't executing
    //    @Test
    public void testExportXls() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);

        boolean isReady = waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        // todo: Actions must be performed in when
        if (isReady) {
            driver.findElement(By.id(BUTTON_XLS)).click();
//            checkAlert();
            Thread.sleep(2000);
            File expectedXlsPath = new File(PATH_RESOURCES + EXPECTED_XLS);
            String expectedXls = documentConverter.parseToString(expectedXlsPath);

            File actualXlsPath = new File(PATH_RESOURCES + ACTUAL_XLS);
            String actualXls = documentConverter.parseToString(actualXlsPath);
            actualXlsPath.delete();

            assertEquals(expectedXls, actualXls);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    // todo: This method is newer used
    private void checkAlert() {
            final int TIME_WAIT_SECONDS = 2;
            WebDriverWait wait = new WebDriverWait(driver, TIME_WAIT_SECONDS);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
    }
}
