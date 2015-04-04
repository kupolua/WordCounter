package info.deepidea.wordcounter.utils;

public class Constants {
    public static final long SECOND = 1_000;
    public static final long DEFAULT_TIMEOUT = 30 * SECOND;
    public static final int PORT = 8080;
    public static final String PARAM_TEXT_COUNT = "textCount";
    public static final String DEPTH = "crawlDepth";
    public static final String INTERNAL_ONLY = "crawlScope";
    public static final String PARAM_SORTING_ORDER = "sortingOrder";
    public static final String PARAM_IS_FILTER_WORDS = "isFilterWords";
    public static final String PARAM_LANGUAGE = "Accept-Language";
    public static final String PARAM_CONTENT_TYPE = "Content-Type";
    public static final String SERVER_NAME = "http://localhost:";
    public static final String CONTEXT = "/WordCounter/";
    public static final String COUNT_REQUEST = "countWords";
    public static final String COUNT_URL = SERVER_NAME + PORT + CONTEXT + COUNT_REQUEST;
    public static final String PATH_RESOURCES = "src/functionalTest/resources/";
    public static final String LANGUAGE_DEFAULT_EN = "en-EN,en;q=0.5";
    public static final String LANGUAGE_RU = "ru";
    public static final String LANGUAGE_UK = "uk";
    public static final String LANGUAGE_ES = "es";
    public static final String LANGUAGE_DE = "de";
    public static final String KEY_ASCENDING = "KEY_ASCENDING";
    public static final String KEY_DESCENDING = "KEY_DESCENDING";
    public static final String VALUE_ASCENDING = "VALUE_ASCENDING";
    public static final String VALUE_DESCENDING = "VALUE_DESCENDING";
}
