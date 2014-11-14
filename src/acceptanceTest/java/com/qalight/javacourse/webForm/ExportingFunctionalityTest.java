package com.qalight.javacourse.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;

import static com.qalight.javacourse.webForm.utils.Constants.*;
import static com.qalight.javacourse.webForm.utils.Util.*;
import static org.junit.Assert.assertTrue;

public class ExportingFunctionalityTest {
    private static WebDriver driver;
    public static String pathResources;

    private final int waitTime = 1000;

    @BeforeClass
    public static void init() {
        final String pathWindowsOs = "C:\\";
        final String pathMacOs = "/Users/";
        //todo add path when os is linux or other
        if (isMacOs()){
            pathResources = pathMacOs;
        } else {
            pathResources = pathWindowsOs;
        }
        FirefoxProfile profile = getProfile(pathResources);
        driver = new FirefoxDriver(profile);
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void testExportPdf() throws Exception {
        // given
        driver.get(BASE_URL);
        final String pdfName = "calculatedWords.pdf";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(BUTTON_PDF)).click();
        Thread.sleep(waitTime);

        File pdfFile = new File(pathResources + pdfName);
        boolean isFileExist = pdfFile.exists();
        pdfFile.delete();

        // then
        assertTrue(isFileExist);
    }

    @Test
    public void testExportXls() throws Exception {
        // given
        driver.get(BASE_URL);
        final String xlsName = "calculatedWords.xls";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(BUTTON_XLS)).click();
        Thread.sleep(waitTime);

        File xlsFile = new File(pathResources + xlsName);
        boolean isFileExist = xlsFile.exists();
        xlsFile.delete();

        // then
        assertTrue(isFileExist);
    }

    private static FirefoxProfile getProfile(String pathResources){
        FirefoxProfile profile = new FirefoxProfile();

        final int indicatorFolder = 2;
        profile.setPreference("browser.download.folderList", indicatorFolder);
        profile.setPreference("browser.download.dir", pathResources);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/html,text/x-csv,application/x-download," +
                "application/vnd.ms-excel,application/pdf,application/msexcel");
        profile.setPreference("pdfjs.disabled", true);
        return profile;
    }

    private static boolean isMacOs() {
        boolean result = false;
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            result = true;
        }
        return result;
    }
}
