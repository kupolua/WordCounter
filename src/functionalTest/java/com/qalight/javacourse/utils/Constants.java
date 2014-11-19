package com.qalight.javacourse.utils;

public class Constants {
    public static final long SECOND = 1_000;
    public static final long DEFAULT_TIMEOUT = 30 * SECOND;
    public static final int PORT = 8080;
    public static final String PARAM_TEXT_COUNT = "textCount";
    public static final String PARAM_SORTING_ORDER = "sortingOrder";
    public static final String PARAM_IS_FILTER_WORDS = "isFilterWords";
    public static final String SERVER_NAME = "http://localhost:";
    public static final String CONTEXT = "/WordCounter/";
    public static final String COUNT_REQUEST = "countWordsRestStyle";
    public static final String COUNT_URL = SERVER_NAME + PORT + CONTEXT + COUNT_REQUEST;
    public static final String PATH_RESOURCES = "src/functionalTest/resources/";
    public static final String KEY_ASCENDING = "KEY_ASCENDING";
    public static final String KEY_DESCENDING = "KEY_DESCENDING";
    public static final String VALUE_ASCENDING = "VALUE_ASCENDING";
    public static final String VALUE_DESCENDING = "VALUE_DESCENDING";
}
