package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static info.deepidea.wordcounter.webForm.utils.Constants.BASE_URL;
import static info.deepidea.wordcounter.webForm.utils.Util.getWebDriver;
import static org.junit.Assert.assertTrue;

public class InformationAboutFunctionalityTest {
    private static final int WAIT_TIME = 3000;
    private static WebDriver driver;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void openAboutProjectPage_fromMenuTab() throws Exception {
        // given
        driver.get(BASE_URL);
        final String elementIdAboutUs = "aboutUsLink";
        final String elementIdAboutUsText = "aboutText";

        // when
        driver.findElement(By.id(elementIdAboutUs)).click();
        Thread.sleep(WAIT_TIME);

        // then
        boolean isAboutDisplayed = driver.findElement(By.id(elementIdAboutUsText)).isDisplayed();
        assertTrue(isAboutDisplayed);
    }

    @Test
    public void openAboutProjectPage_fromLinkInText() throws InterruptedException {
        // given
        driver.get(BASE_URL);
        final String elementIdAboutUsText = "aboutText";
        String aboutUsCssSelector = "#welcomeText > a";

        // when
        driver.findElement(By.cssSelector(aboutUsCssSelector)).click();
        Thread.sleep(WAIT_TIME);

        // then
        boolean isAboutTextDisplayed = driver.findElement(By.id(elementIdAboutUsText)).isDisplayed();
        assertTrue(isAboutTextDisplayed);
    }
}
