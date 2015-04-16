package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static info.deepidea.wordcounter.webForm.utils.Util.getWebDriver;
import static info.deepidea.wordcounter.webForm.utils.Constants.*;
import static info.deepidea.wordcounter.webForm.utils.Util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ResultsPresentationFunctionalityTest {
    private static final int TIME_WAIT_SPOILER = 1000;
    private static final String ELEMENT_DATA_TABLES_LENGTH = "countedWords_length";
    private static final String ELEMENT_ID_LINK_NEXT = "countedWords_next";
    private static final String DATA_TABLES_LENGTH_100 = "100";
    private static final String HTML_TEST_PAGE_PRESENTATION = "http://deepidea.info/wordcounter/testData/textForTestShowEntries.html";
    private static final String EXPECTED_BY_DEFAULT = "входят 1\n" +
            "intellij 1\n" +
            "новые 1\n" +
            "последовательности 1\n" +
            "программистов 1\n" +
            "многие 1\n" +
            "разработка 1\n" +
            "обновляет 1\n" +
            "java 1\n" +
            "через 1";
    private static final String ELEMENT_ID_LINK_PREV = "countedWords_previous";
    private static final String RU_ALPHABET_LINK = "http://deepidea.info/wordcounter/testData/RU_alphabet.docx";
    private static final String EN_ALPHABET_LINK = "http://deepidea.info/wordcounter/testData/EN_alphabet.docx";
    private static final String X_PATH_LAST_PAGE = "//*[@id=\"countedWords_paginate\"]/ul/li[7]/a";
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
    public void testWordsByDefault() {
        // given
        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE_PRESENTATION);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_BY_DEFAULT, actualResult);
    }

    @Test
    public void testShowEntries25() throws Exception {
        // given
        driver.get(BASE_URL);
        final String dataTablesLength25 = "25";
        final String expectedShowEntries25 = "входят 1\n" +
                "intellij 1\n" +
                "новые 1\n" +
                "последовательности 1\n" +
                "программистов 1\n" +
                "многие 1\n" +
                "разработка 1\n" +
                "обновляет 1\n" +
                "java 1\n" +
                "через 1\n" +
                "этой 1\n" +
                "пришло 1\n" +
                "нам 1\n" +
                "javafx 1\n" +
                "электронные 1\n" +
                "отличается 1\n" +
                "участников 1\n" +
                "результат 1\n" +
                "решили 1\n" +
                "новая 1\n" +
                "первой 1\n" +
                "интерфейсы 1\n" +
                "человек 1\n" +
                "в 1\n" +
                "актуальные 1";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE_PRESENTATION);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        new Select(driver.findElement(By.name(ELEMENT_DATA_TABLES_LENGTH))).selectByVisibleText(dataTablesLength25);

        // then
        String actualShowEntries = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedShowEntries25, actualShowEntries);
    }

    @Test
    public void testShowEntries50() throws Exception {
        // given
        driver.get(BASE_URL);
        final String dataTablesLength50 = "50";
        final String expectedShowEntries50 = "входят 1\n" +
                "intellij 1\n" +
                "новые 1\n" +
                "последовательности 1\n" +
                "программистов 1\n" +
                "многие 1\n" +
                "разработка 1\n" +
                "обновляет 1\n" +
                "java 1\n" +
                "через 1\n" +
                "этой 1\n" +
                "пришло 1\n" +
                "нам 1\n" +
                "javafx 1\n" +
                "электронные 1\n" +
                "отличается 1\n" +
                "участников 1\n" +
                "результат 1\n" +
                "решили 1\n" +
                "новая 1\n" +
                "первой 1\n" +
                "интерфейсы 1\n" +
                "человек 1\n" +
                "в 1\n" +
                "актуальные 1\n" +
                "появились 1\n" +
                "лучших 1\n" +
                "стандартных 1\n" +
                "и 1\n" +
                "отзывы 1\n" +
                "стали 1\n" +
                "о 1\n" +
                "чем 1\n" +
                "вдохновляет 1\n" +
                "использоваться 1\n" +
                "которые 1\n" +
                "тренинга 1\n" +
                "поэтому 1\n" +
                "например 1\n" +
                "ваши 1\n" +
                "полного 1\n" +
                "это 1\n" +
                "изменились 1\n" +
                "время 1\n" +
                "версия 1\n" +
                "вместо 1\n" +
                "создать 1\n" +
                "такой 1\n" +
                "цифра 1\n" +
                "плюс 1";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE_PRESENTATION);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        new Select(driver.findElement(By.name(ELEMENT_DATA_TABLES_LENGTH))).selectByVisibleText(dataTablesLength50);

        // then
        String actualShowEntries = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedShowEntries50, actualShowEntries);
    }

    @Test
    public void testShowEntries50_withNextAndPreviousPage() throws Exception {
        // given
        driver.get(BASE_URL);
        final String dataTablesLength50 = "50";
        final String expectedResult50 = "а 2\n" + "б 2\n" + "в 2\n" + "г 2\n" + "е 2\n" + "з 2\n" + "и 2\n" +
                "й 2\n" + "к 2\n" + "л 2\n" + "м 2\n" + "н 2\n" + "о 2\n" + "п 2\n" + "с 2\n" + "т 2\n" + "у 2\n" +
                "ф 2\n" + "х 2\n" + "ц 2\n" + "ч 2\n" + "ш 2\n" + "щ 2\n" + "ъ 2\n" + "ы 2\n" + "ь 2\n" + "э 2\n" +
                "ю 2\n" + "a 2\n" + "b 2\n" + "c 2\n" + "d 2\n" + "e 2\n" + "f 2\n" + "g 2\n" + "h 2\n" + "i 2\n" +
                "j 2\n" + "k 2\n" + "l 2\n" + "m 2\n" + "n 2\n" + "p 2\n" + "q 2\n" + "s 2\n" + "u 2\n" + "v 2\n" +
                "w 2\n" + "x 2\n" + "y 2";
        final String expectedResult8 = "д 1\n" + "ж 1\n" + "р 1\n" + "я 1\n" + "o 1\n" + "r 1\n" + "t 1\n" + "z 1";

        // when
        putDataAndClickCountButton(driver, RU_ALPHABET_LINK + SEPARATOR + EN_ALPHABET_LINK);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        new Select(driver.findElement(By.name(ELEMENT_DATA_TABLES_LENGTH))).selectByVisibleText(dataTablesLength50);

        driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();
        String actualResult8 = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();

        driver.findElement(By.id(ELEMENT_ID_LINK_PREV)).click();
        String actualResult50 = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();

        // then
        assertEquals(expectedResult8, actualResult8);
        assertEquals(expectedResult50, actualResult50);
    }

    @Test
    public void testShowEntries100() throws Exception {
        // given
        driver.get(BASE_URL);
        final String expectedShowEntries100 = "входят 1\n" +
                "intellij 1\n" +
                "новые 1\n" +
                "последовательности 1\n" +
                "программистов 1\n" +
                "многие 1\n" +
                "разработка 1\n" +
                "обновляет 1\n" +
                "java 1\n" +
                "через 1\n" +
                "этой 1\n" +
                "пришло 1\n" +
                "нам 1\n" +
                "javafx 1\n" +
                "электронные 1\n" +
                "отличается 1\n" +
                "участников 1\n" +
                "результат 1\n" +
                "решили 1\n" +
                "новая 1\n" +
                "первой 1\n" +
                "интерфейсы 1\n" +
                "человек 1\n" +
                "в 1\n" +
                "актуальные 1\n" +
                "появились 1\n" +
                "лучших 1\n" +
                "стандартных 1\n" +
                "и 1\n" +
                "отзывы 1\n" +
                "стали 1\n" +
                "о 1\n" +
                "чем 1\n" +
                "вдохновляет 1\n" +
                "использоваться 1\n" +
                "которые 1\n" +
                "тренинга 1\n" +
                "поэтому 1\n" +
                "например 1\n" +
                "ваши 1\n" +
                "полного 1\n" +
                "это 1\n" +
                "изменились 1\n" +
                "время 1\n" +
                "версия 1\n" +
                "вместо 1\n" +
                "создать 1\n" +
                "такой 1\n" +
                "цифра 1\n" +
                "плюс 1\n" +
                "swing 1\n" +
                "свои 1\n" +
                "добавилось 1\n" +
                "версии 1\n" +
                "тем 1\n" +
                "чтобы 1\n" +
                "будет 1\n" +
                "старт 1\n" +
                "прохождения 1\n" +
                "нашли 1\n" +
                "одной 1\n" +
                "большая 1\n" +
                "приходило 1\n" +
                "добавляет 1\n" +
                "работу 1\n" +
                "тренинг 1\n" +
                "мы 1\n" +
                "на 1\n" +
                "сюда 1\n" +
                "регистрация 1\n" +
                "отличия 1\n" +
                "быстрый 1\n" +
                "много 1\n" +
                "но 1\n" +
                "основные 1\n" +
                "обновления 1\n" +
                "инструменты 1\n" +
                "ide 1\n" +
                "для 1\n" +
                "материалов 1\n" +
                "уверенно 1\n" +
                "более 1\n" +
                "от 1\n" +
                "idea 1\n" +
                "идти 1\n" +
                "прошло 1\n" +
                "программировать 1\n" +
                "полностью 1\n" +
                "материалы 1\n" +
                "после 1\n" +
                "что 1\n" +
                "диктует 1\n" +
                "были 1\n" +
                "части 1\n" +
                "очень 1\n" +
                "библиотек 1\n" +
                "темы 1\n" +
                "писем 1\n" +
                "связка 1\n" +
                "условия 1";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE_PRESENTATION);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        new Select(driver.findElement(By.name(ELEMENT_DATA_TABLES_LENGTH))).selectByVisibleText(DATA_TABLES_LENGTH_100);

        // then
        String actualShowEntries = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedShowEntries100, actualShowEntries);
    }

    @Test
    public void testShowEntries10() throws Exception {
        // given
        driver.get(BASE_URL);
        final String dataTablesLength10 = "10";

        // when
        putDataAndClickCountButton(driver, HTML_TEST_PAGE_PRESENTATION);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        new Select(driver.findElement(By.name(ELEMENT_DATA_TABLES_LENGTH))).selectByVisibleText(DATA_TABLES_LENGTH_100);
        new Select(driver.findElement(By.name(ELEMENT_DATA_TABLES_LENGTH))).selectByVisibleText(dataTablesLength10);

        // then
        String actualShowEntries = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(EXPECTED_BY_DEFAULT, actualShowEntries);
    }

    @Test
    public void testNextPage() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "м 2\n" + "н 2\n" + "о 2\n" + "п 2\n" + "с 2\n" + "т 2\n" + "у 2\n" + "ф 2\n" +
                "х 2\n" + "ц 2";

        // when
        putDataAndClickCountButton(driver, RU_ALPHABET_LINK + SEPARATOR + EN_ALPHABET_LINK);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testPreviousPage() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "а 2\n" + "б 2\n" + "в 2\n" + "г 2\n" + "е 2\n" + "з 2\n" + "и 2\n" + "й 2\n" +
                "к 2\n" + "л 2";

        // when
        putDataAndClickCountButton(driver, RU_ALPHABET_LINK + SEPARATOR + EN_ALPHABET_LINK);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.id(ELEMENT_ID_LINK_NEXT)).click();
        driver.findElement(By.id(ELEMENT_ID_LINK_PREV)).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testLastPage() {
        // given
        driver.get(BASE_URL);
        final String expectedResult = "д 1\n" + "ж 1\n" + "р 1\n" + "я 1\n" + "o 1\n" + "r 1\n" + "t 1\n" + "z 1";

        // when
        putDataAndClickCountButton(driver, RU_ALPHABET_LINK + SEPARATOR + EN_ALPHABET_LINK);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.xpath(X_PATH_LAST_PAGE)).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFirstPage() {
        // given
        driver.get(BASE_URL);
        final String xPathFirstPage = "//*[@id=\"countedWords_paginate\"]/ul/li[2]/a";
        final String expectedResult = "а 2\n" + "б 2\n" + "в 2\n" + "г 2\n" + "е 2\n" + "з 2\n" + "и 2\n" + "й 2\n" +
                "к 2\n" + "л 2";

        // when
        putDataAndClickCountButton(driver, RU_ALPHABET_LINK + SEPARATOR + EN_ALPHABET_LINK);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.xpath(X_PATH_LAST_PAGE)).click();
        driver.findElement(By.xpath(xPathFirstPage)).click();

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSpoiler_close() throws Exception {
        // given
        final String htmlBrokenPageLatin = "http://kupol....in.ua/wordcounter/testData/test_page_latin.html";
        final String txtLink = "http://deepidea.info/wordcounter/testData/letters%2Bnumbers.txt";

        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, htmlBrokenPageLatin + SEPARATOR + txtLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        String elementCssErrorContainer = "#errorsContainer";
        final boolean isSpoilerOpen = driver.findElement(By.cssSelector(elementCssErrorContainer)).isDisplayed();
        assertFalse(isSpoilerOpen);
    }

    @Test
    public void testSpoiler_notUse() throws Exception {
        // given
        final String txtLink = "http://deepidea.info/wordcounter/testData/letters%2Bnumbers.txt";

        driver.get(BASE_URL);

        // when
        putDataAndClickCountButton(driver, txtLink);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        //then
        String elementIdErrorSpoiler = "errorsSpoiler";
        final boolean isSpoilerUse = driver.findElement(By.id(elementIdErrorSpoiler)).isDisplayed();
        assertFalse(isSpoilerUse);
    }
}

