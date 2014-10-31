package com.qalight.javacourse.webForm.utils;

public class Constants {
    public static final int WAIT_FOR_ELEMENT = 20;
    public static final int DEFAULT_WAIT_FOR_PAGE = 90;
    public static final int PORT = 8080;
    public static final String HTML_TEST_PAGE = "http://defas.com.ua/java/textForTest.html";
    public static final String CONTEXT = "/WordCounter/";
    public static final String BASE_URL = "http://localhost:" + PORT + CONTEXT;
    public static final String BUTTON_ID_COUNT_WORDS = "CountWords";
    public static final String ANCHOR_HTML_PAGE_WITH_WORDS = "#countedWords > tbody";
    public static final String ELEMENT_ID_TEXT_AREA = "textCount";
    public static final String PATH_RESOURCES = "src/functionalTest/resources/";
    public static final String BUTTON_PDF = "getPdf";
    public static final String EXPECTED_XLS = "expectedXls.xls";
    public static final String ACTUAL_XLS = "calculatedWords.xls";
    public static final String BUTTON_XLS = "getXls";
    public static final String EXPECTED_STANDARD_RESULT = "one 4\n" + "ёлка 3\n" + "two 3\n" + "білка 3\n" + "объем " +
            "3\n" + "їжак 2\n" + "объём 1\n" + "ученики 1\n" + "і 1\n" + "але 1";
    public static final String SEPARATOR = " ";
}
