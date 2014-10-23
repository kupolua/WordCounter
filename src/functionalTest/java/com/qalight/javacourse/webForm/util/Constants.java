package com.qalight.javacourse.webForm.util;

public class Constants {
    public static final int WAIT_FOR_ELEMENT = 15;
    public static final int DEFAULT_WAIT_FOR_PAGE = 60;
    public static final int PORT = 8080;
    public static final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";
    public static final String CONTEXT = "/WordCounter/";
    public static final String BASE_URL = "http://localhost:" + PORT + CONTEXT;
    public static final String RESPONSE_IS_NOT_READY = "Verify Failed: Response is not ready";
    public static final String BUTTON_ID_COUNT_WORDS = "CountWords";
    public static final String ELEMENT_ID_TEXT_AREA = "textCount";
    public static final String PATH_RESOURCES = "src/functionalTest/resources/";
    public static final String BUTTON_PDF = "getPdf";
    public static final String EXPECTED_XLS = "expectedXls.xls";
    public static final String ACTUAL_XLS = "calculatedWords.xls";
    public static final String BUTTON_XLS = "getXls";
}
