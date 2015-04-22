package info.deepidea.wordcounter.webForm;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static info.deepidea.wordcounter.webForm.utils.Constants.*;
import static info.deepidea.wordcounter.webForm.utils.Util.getWebDriver;
import static info.deepidea.wordcounter.webForm.utils.Util.putDataAndClickCountButton;
import static info.deepidea.wordcounter.webForm.utils.Util.waitForJQueryProcessing;
import static org.junit.Assert.assertEquals;

public class LocalizationFunctionalityTest {
    private static final String ID_STRING_AT_WEB_FORM = "p1";
    private WebDriver driver;
    private static WebDriverWait wait;

    @Test
    public void testImproperInput_en() throws Exception {
        // given
        final String localization = "en";
        final String improperInput = "kris@gmail.com";
        final String expectedResult = "System cannot count entered text. Did you forget to add " +
                "'http://' to the link or entered not readable text?";
        driver = getWebDriver(localization);
        wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT);

        // when
        driver.get(BASE_URL);
        putDataAndClickCountButton(driver, improperInput);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementCssErrorSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        // then
        String actualResult = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testImproperInput_ru() throws Exception {
        // given
        final String localization = "ru";
        final String improperInput = "www.google.com";
        final String expectedResult = "Система не может обработать введенный текст. Пожалуйста, проверьте, " +
                "не забыли ли Вы добавить 'http://' префикс к ссылке или ввели нечитаемый текст.";
        driver = getWebDriver(localization);
        wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT);

        // when
        driver.get(BASE_URL);
        putDataAndClickCountButton(driver, improperInput);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementCssErrorSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        // then
        String actualResult = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testImproperInput_uk() throws Exception {
        // given
        final String localization = "uk";
        final String improperInput = "%/*\\^#";
        final String expectedResult = "Система не взмозі обробити введений текст. Будь ласка, перевірте, " +
                "чи ви не забули додати 'http://' префікс до посилання або ввели нечитабельний текст.";
        driver = getWebDriver(localization);
        wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT);

        // when
        driver.get(BASE_URL);
        putDataAndClickCountButton(driver, improperInput);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementCssErrorSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        // then
        String actualResult = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testImproperInput_fr() throws Exception {
        // given
        final String localization = "fr";
        final String improperInput = "0";
        final String expectedResult = "System cannot count entered text. Did you forget to add " +
                "'http://' to the link or entered not readable text?";
        driver = getWebDriver(localization);
        wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT);

        // when
        driver.get(BASE_URL);
        putDataAndClickCountButton(driver, improperInput);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        Thread.sleep(2000);
        driver.findElement(By.className(elementCssErrorSpoilerOpen)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)));

        // then
        String actualResult = driver.findElement(By.cssSelector(ELEMENT_CSS_ERROR_CONTAINER)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEnglishLocal() {
        // given
        final String localizationEn = "en";
        final String expectedResult = "Get the statistic of most frequently used words in one click!";
        driver = getWebDriver(localizationEn);

        // when
        driver.get(BASE_URL);

        // then
        String actualResult = driver.findElement(By.id(ID_STRING_AT_WEB_FORM)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRussianLocal() {
        // given
        final String localizationRu = "ru";
        final String expectedResult = "Получите статистику наиболее часто используемых слов в один миг!";
        driver = getWebDriver(localizationRu);

        // when
        driver.get(BASE_URL);

        // then
        String actualResult = driver.findElement(By.id(ID_STRING_AT_WEB_FORM)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testUkraineLocal() {
        // given
        final String localizationUk = "uk";
        final String expectedResult = "Отримайте статистику слів, що найчастіше зустрічаються у тексті, всього за одну" +
                " мить!";
        driver = getWebDriver(localizationUk);

        // when
        driver.get(BASE_URL);

        // then
        String actualResult = driver.findElement(By.id(ID_STRING_AT_WEB_FORM)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGermanyLocal() {
        // given
        final String localizationDe = "de";
        final String expectedResult = "Get the statistic of most frequently used words in one click!";
        driver = getWebDriver(localizationDe);

        // when
        driver.get(BASE_URL);

        // then
        String actualResult = driver.findElement(By.id(ID_STRING_AT_WEB_FORM)).getText();
        driver.quit();
        assertEquals(expectedResult, actualResult);
    }
}
