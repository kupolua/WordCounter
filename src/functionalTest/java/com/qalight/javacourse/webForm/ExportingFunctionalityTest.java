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
import static com.qalight.javacourse.webForm.utils.Util.*;
import static org.junit.Assert.assertEquals;

public class ExportingFunctionalityTest {
    private static WebDriver driver;
    private static Tika documentConverter;

    private final int waitTime = 2500;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
        documentConverter = new Tika();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

//    @Test
    public void testExportPdf() throws Exception {
        // given
        driver.get(BASE_URL);
        final String expectedPdf = "expectedPdf.pdf";
        final String actualPdf = "calculatedWords.pdf";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(BUTTON_PDF)).click();
        //checkAlert();
        //todo after fix checkAlert we delete next line
        Thread.sleep(waitTime);

        File expectedPdfPath = new File(PATH_RESOURCES + expectedPdf);
        String expectedPdfResult = documentConverter.parseToString(expectedPdfPath);

        File actualPdfPath = new File(PATH_RESOURCES + actualPdf);
        String actualPdfResult = documentConverter.parseToString(actualPdfPath);
        actualPdfPath.delete();

        // then
        assertEquals(expectedPdfResult, actualPdfResult);
    }

//    @Test
    public void testExportXls() throws Exception {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(BUTTON_XLS)).click();
        //checkAlert();
        //todo after fix checkAlert we delete next line
        Thread.sleep(waitTime);
        File expectedXlsPath = new File(PATH_RESOURCES + EXPECTED_XLS);
        String expectedXls = documentConverter.parseToString(expectedXlsPath);

        File actualXlsPath = new File(PATH_RESOURCES + ACTUAL_XLS);
        String actualXls = documentConverter.parseToString(actualXlsPath);
        actualXlsPath.delete();

        // then
        assertEquals(expectedXls, actualXls);
    }

    private void checkAlert() {
        final int timeWaitSeconds = 2;
        WebDriverWait wait = new WebDriverWait(driver, timeWaitSeconds);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
