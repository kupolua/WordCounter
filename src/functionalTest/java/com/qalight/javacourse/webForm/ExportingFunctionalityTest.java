package com.qalight.javacourse.webForm;

import com.qalight.javacourse.webForm.util.Util;
import org.apache.tika.Tika;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.qalight.javacourse.webForm.util.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExportingFunctionalityTest {
    private WebDriver driver;
    private static Tika documentConverter = new Tika();
    private static final Logger LOG = LoggerFactory.getLogger(ExportingFunctionalityTest.class);
    private FirefoxProfile profile;

    @Before
    public void startWebDriver() {
//        profile = new FirefoxProfile();
//        profile.setPreference("browser.download.folderList", 2);
//        profile.setPreference("browser.download.dir", PATH_RESOURCES);
//        profile.setPreference("browser.download.manager.showWhenStarting", false);
//        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");

//        driver = new FirefoxDriver(profile);
        driver = new SafariDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE, TimeUnit.SECONDS);
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testExportPdf() throws Exception {
        // given
//        driver.get(BASE_URL);

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = Util.waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then

       if (isReady) {
            driver.findElement(By.id(BUTTON_PDF)).click();
            checkAlert();

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

    @Test
    public void testExportXls() throws Exception {
        // given
//        driver.get(BASE_URL);
        File expectedXlsPath = new File(PATH_RESOURCES + EXPECTED_XLS);
        String expectedXls = documentConverter.parseToString(expectedXlsPath);

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).clear();
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).sendKeys(HTML_TEST_PAGE);
        driver.findElement(By.id(BUTTON_ID_COUNT_WORDS)).click();

        boolean isReady = Util.waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        if (isReady) {
            driver.findElement(By.id(BUTTON_XLS)).click();
            checkAlert();

            File actualXlsPath = new File(PATH_RESOURCES + ACTUAL_XLS);
            String actualXls = documentConverter.parseToString(actualXlsPath);
            actualXlsPath.delete();

            assertEquals(expectedXls, actualXls);
        } else {
            fail(RESPONSE_IS_NOT_READY);
        }
    }

    public void checkAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            //exception handling
        }
    }
}
