package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;

import static info.deepidea.wordcounter.webForm.utils.Constants.*;
import static info.deepidea.wordcounter.webForm.utils.Util.putDataAndClickCountButton;
import static info.deepidea.wordcounter.webForm.utils.Util.waitForJQueryProcessing;
import static org.junit.Assert.assertTrue;

public class ExportingFunctionalityTest {
    private static final int WAIT_TIME = 1000;
    private static WebDriver driver;
    private static String pathResources;

    @BeforeClass
    public static void init() {
        final String userDirectory = System.getProperty("user.dir");
        final String winPath = "\\src\\acceptanceTest\\resources\\";
        final String macPath = "/src/acceptanceTest/resources/";
        final String pathWindowsOs = userDirectory + winPath;
        final String pathMacOs = userDirectory + macPath;

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
        final String pageLink = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";
        final String pdfName = "calculatedWords.pdf";

        // when
        putDataAndClickCountButton(driver, pageLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(BUTTON_PDF)).click();
        Thread.sleep(WAIT_TIME);

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
        final String pageLink = "http://kupol.in.ua/wordcounter/testData/page_cyrillic.html";
        final String xlsName = "calculatedWords.xls";

        // when
        putDataAndClickCountButton(driver, pageLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(BUTTON_XLS)).click();
        Thread.sleep(WAIT_TIME);

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
