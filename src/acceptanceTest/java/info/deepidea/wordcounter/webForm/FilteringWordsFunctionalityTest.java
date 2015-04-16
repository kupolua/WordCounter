package info.deepidea.wordcounter.webForm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static info.deepidea.wordcounter.webForm.utils.Constants.*;
import static info.deepidea.wordcounter.webForm.utils.Util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class FilteringWordsFunctionalityTest {
    private static final int WAIT_TIME = 3000;
    private static final String BUTTON_ID_FILTERING_WORDS = "filterCheck";
    private static final String BUTTON_ID_UN_FILTERING_WORDS = "buttonGetUnFilterWords";
    private static final String ELEMENT_SHOW_FILTER = "#filterShow";
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void init() {
        driver = getWebDriver();
        wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT);
    }

    @AfterClass
    public static void quitWebDriver() {
        driver.quit();
    }

    @Test
    public void testFilterWords_latin_html() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputHtmlUrl = "http://deepidea.info/wordcounter/testData/page_latin.html";
        final String expectedResult = "test 3\nsanta-monica 1";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
        putDataAndClickCountButton(driver, inputHtmlUrl);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(BUTTON_ID_UN_FILTERING_WORDS)));

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFilterWords_cyrillic_text() throws Exception {
        // given
        driver.get(BASE_URL);
        final String cyrillicText = "Під'їзд, ПІД'ЇЗД, ґедзь, єнот, й";
        final String expectedResult = "під'їзд 2\nєнот 1\nґедзь 1";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();

        putDataAndClickCountButton(driver, cyrillicText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

//        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(BUTTON_ID_UN_FILTERING_WORDS)));

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFilterWords_french_text() throws Exception {
        // given
        driver.get(BASE_URL);
        final String frenchText = "Venez découvrir la magie des saisons de l'année en français!";
        final String expectedResult = "de 1\nl'anne 1\nmagie 1\ndes 1\nla 1\nen 1\nvenez 1\ndcouvrir 1\nsaisons 1\n" +
                "franais 1";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();

        putDataAndClickCountButton(driver, frenchText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

//        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(BUTTON_ID_UN_FILTERING_WORDS)));

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFilterWords_cyrillicUrl_pptx() throws Exception {
        // given
        driver.get(BASE_URL);
        final String frenchText =
                "http://deepidea.info/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx";
        final String expectedResult = "думи 2\nмої 1";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();

        putDataAndClickCountButton(driver, frenchText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

//        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonGetUnFilterWords")));

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRemoveFilter_latinUrl_html() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputLatinUrl = "http://deepidea.info/wordcounter/testData/page_latin.html";
        final String expectedResult = "test 3\na 1\nsanta-monica 1";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();

        putDataAndClickCountButton(driver, inputLatinUrl);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();

        putDataAndClickCountButton(driver, inputLatinUrl);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRemoveFilter_cyrillic_text() throws Exception {
        // given
        driver.get(BASE_URL);
        final String cyrillicText = "Під'їзд, ПІД'ЇЗД, ґедзь, єнот, й";
        final String expectedResult = "під'їзд 2\nєнот 1\nґедзь 1\nй 1";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
        putDataAndClickCountButton(driver, cyrillicText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
        putDataAndClickCountButton(driver, cyrillicText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRemoveFilter_cyrillicUrl_pptx() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inputPptxUrl =
                "http://deepidea.info/wordcounter/testData/%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0.pptx";
        final String expectedResult = "думи 2\nмої 1";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
        putDataAndClickCountButton(driver, inputPptxUrl);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();
        putDataAndClickCountButton(driver, inputPptxUrl);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        // then
        String actualResult = driver.findElement(By.cssSelector(ANCHOR_HTML_PAGE_WITH_WORDS)).getText();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testOpenFilter_showFilter() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inessentialText = "word";
        final String elementIdFilteringWords = "wordsFilter";
        final String expectedWordsFromFilter = "the a an to and of in will he i is for his your they with not that " +
                "him be them it who from on all my have was as me but are this their so then when had were what by " +
                "has at or up we there if no her those into before our am these its she o about also through other " +
                "after how which where would each any some why than off while until yet nor s b c d e f g h j k l m " +
                "n o p q r s t u v w x y z в вы своего вами она нем тебе кого и в не что его на он я же но ибо с а " +
                "как от к из в чтобы ему то вам они по вас если когда им о их все во кто за ты мы меня мне это у " +
                "есть для так потому будет ли был или ни бы тогда который да нас него тебя тот было итак тебя нам " +
                "них которые себя того были ее всех со вот до пред перед еще быть всем уже сего которого между " +
                "только нему нет сие это себе дабы через там где ними при сам своих тем сей мой посему над ничего " +
                "соего собою чем некоторые свою ко после своих ей под об те г д ж з й к л м н о п р т ф х ц ч ш щ ъ " +
                "ь ы э ю і з що до його та як було її за про й були але р від для він їх лише між вони же це тому чи " +
                "тим тільки своїх там над так навіть зокрема проте які при цього ж все всі всіх є свого т таким під " +
                "після н ці серед цьому е своєї який ще із вона цих них зі також свої м уже цей коли через перед той " +
                "отже може тобто якому того ні ними яка своїм щоб якого своє всього свій себе своїй якої якій або б " +
                "вже без їхній їхнього їхні своїми де по ним ній тут йому буду яким тих то їм нього їхніх те цю мав " +
                "собі ньому з-під цій деякі крім би всю ї ґ";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();

        putDataAndClickCountButton(driver, inessentialText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);

        driver.findElement(By.cssSelector(ELEMENT_SHOW_FILTER)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementIdFilteringWords)));

        // then
        String actualWordsFromFilter = driver.findElement(By.id(elementIdFilteringWords)).getText();
        assertEquals(expectedWordsFromFilter, actualWordsFromFilter);
    }

    @Test
    public void testCloseFilter_XClose() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inessentialText = "word";
        final String idModalWindow = "simplemodal-placeholder";
        final String linkTextX = "x";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();

        putDataAndClickCountButton(driver, inessentialText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(ELEMENT_SHOW_FILTER)).click();
        driver.findElement(By.linkText(linkTextX)).click();
        Thread.sleep(WAIT_TIME);

        // then
        boolean isModalWindow = driver.getPageSource().contains(idModalWindow);
        assertFalse(isModalWindow);
    }

    @Test
    public void testCloseFilter_buttonClose() throws Exception {
        // given
        driver.get(BASE_URL);
        final String inessentialText = "word";
        final String idModalWindow = "simplemodal-placeholder";
        final String elementCssModalClose = "button.simplemodal-close";

        // when
        driver.findElement(By.id(ELEMENT_ID_TEXT_AREA)).click();
        driver.findElement(By.id(BUTTON_ID_FILTERING_WORDS)).click();

        putDataAndClickCountButton(driver, inessentialText);
        waitForJQueryProcessing(driver, WAIT_FOR_ELEMENT);
        driver.findElement(By.cssSelector(ELEMENT_SHOW_FILTER)).click();
        driver.findElement(By.cssSelector(elementCssModalClose)).click();
        Thread.sleep(WAIT_TIME);

        // then
        boolean isModalWindow = driver.getPageSource().contains(idModalWindow);
        assertFalse(isModalWindow);
    }
}
