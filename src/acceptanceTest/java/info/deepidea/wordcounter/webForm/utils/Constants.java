package info.deepidea.wordcounter.webForm.utils;

public class Constants {
    public static final int WAIT_FOR_ELEMENT = 20;
    public static final int DEFAULT_WAIT_FOR_PAGE = 35;
    public static final int PORT = 8080;
    public static final String CONTEXT = "/WordSurfer/?";
    public static final String BASE_URL = "http://localhost:" + PORT + CONTEXT;
    public static final String BUTTON_ID_COUNT_WORDS = "getCountedWords";
    public static final String ANCHOR_HTML_PAGE_WITH_WORDS = "#countedWords > tbody";
    public static final String ELEMENT_ID_TEXT_AREA = "textCount";
    public static final String ELEMENT_ID_URL_TAB = "urlTab";
    public static final String ELEMENT_ID_URL_TEXT_AREA = "urlCount";
    public static final String BUTTON_PDF = "getPdf";
    public static final String BUTTON_XLS = "getXls";
    public static final String SEPARATOR = " ";
    public static final String ELEMENT_CSS_ERROR_CONTAINER = "#errorsContainer";
    public static final String elementCssErrorSpoilerOpen = "spoiler_open";
    public static final String elementCssSpoilerOpen = "spoilerStatistic_open";
    public static final String elementCssSpoilerStatisticOpen = "spoilerStatistic_open";
    public static final String STATISTIC_TABLE = "wordStatistic";
}