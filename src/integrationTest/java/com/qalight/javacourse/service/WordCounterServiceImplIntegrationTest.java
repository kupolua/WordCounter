package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class WordCounterServiceImplIntegrationTest {
    private final static String DATA_TYPE_RESPONSE = "json";
    private final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";

    @Autowired
    private WordCounterService wordCounterService;

    // todo: fix this test is failing
    @Test @Ignore
    public void testGetWordCounterResult_okUrl_KeyAscSort_JsonResp() {
        // given
        final String expectedResult = "{\"success\":true,\"dataAjax\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"дом\",\"1\"],[\"нообъем\",\"1\"],[\"єнот\",\"1\"],[\"ёлка\",\"2\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"two\",\"3\"],[\"vkamenniygmailcom\",\"1\"],[\"другнарод\",\"1\"],[\"человек\",\"1\"],[\"http://habrahabr.ru/posts/top/weekly/\",\"1\"],[\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\",\"1\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"іёлка\",\"1\"],[\"завет\",\"1\"],[\"имя\",\"1\"],[\"объем\",\"2\"],[\"слово\",\"1\"],[\"сказал\",\"1\"]]}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_emptyUrl_KeyAscSort_JsonResp() {
        // given
        final String clientRequestEmptyUrl = "";

        // when
        wordCounterService.getWordCounterResult(clientRequestEmptyUrl, DATA_TYPE_RESPONSE);

        // then
        // expected exception
    }

    @Test
    public void testGetWordCounterResult_InvalidUrl_KeyAscSort_JsonResp() {
        // given
        final String clientRequestInvalidUrl = "http://95.158.60.148:8008/kpl/testingPageINVALID.html";
        final String expctedExceptionString = "java.lang.RuntimeException: Can't connect to: http://95.158.60.148:8008/kpl/testingPageINVALID.html";

        // when
        Exception actualException = null;
        try {
            wordCounterService.getWordCounterResult(clientRequestInvalidUrl, DATA_TYPE_RESPONSE);
        } catch (RuntimeException e) {
            actualException = e;
        }

        // then
        if (actualException != null) {
            Assert.assertEquals(expctedExceptionString, actualException.toString());
        } else {
            Assert.assertFalse(true);
        }
    }

    // todo: fix this test is failing
    @Test @Ignore
    public void testGetWordCounterResult_okUrl_KeyDescSort_JsonResp() {
        // given
        final String expectedResult = "{\"success\":true,\"dataAjax\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"дом\",\"1\"],[\"нообъем\",\"1\"],[\"єнот\",\"1\"],[\"ёлка\",\"2\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"two\",\"3\"],[\"vkamenniygmailcom\",\"1\"],[\"другнарод\",\"1\"],[\"человек\",\"1\"],[\"http://habrahabr.ru/posts/top/weekly/\",\"1\"],[\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\",\"1\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"іёлка\",\"1\"],[\"завет\",\"1\"],[\"имя\",\"1\"],[\"объем\",\"2\"],[\"слово\",\"1\"],[\"сказал\",\"1\"]]}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    // todo: fix this test is failing
    @Test @Ignore
    public void testGetWordCounterResult_okUrl_ValAscSort_JsonResp() {
        // given
        final String expectedResult = "{\"success\":true,\"dataAjax\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"дом\",\"1\"],[\"нообъем\",\"1\"],[\"єнот\",\"1\"],[\"ёлка\",\"2\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"two\",\"3\"],[\"vkamenniygmailcom\",\"1\"],[\"другнарод\",\"1\"],[\"человек\",\"1\"],[\"http://habrahabr.ru/posts/top/weekly/\",\"1\"],[\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\",\"1\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"іёлка\",\"1\"],[\"завет\",\"1\"],[\"имя\",\"1\"],[\"объем\",\"2\"],[\"слово\",\"1\"],[\"сказал\",\"1\"]]}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    // todo: fix this test is failing
    @Test @Ignore
    public void testGetWordCounterResult_okUrl_ValDescSort_JsonResp() {
        // given
        final String expectedResult = "{\"success\":true,\"dataAjax\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"дом\",\"1\"],[\"нообъем\",\"1\"],[\"єнот\",\"1\"],[\"ёлка\",\"2\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"two\",\"3\"],[\"vkamenniygmailcom\",\"1\"],[\"другнарод\",\"1\"],[\"человек\",\"1\"],[\"http://habrahabr.ru/posts/top/weekly/\",\"1\"],[\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\",\"1\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"іёлка\",\"1\"],[\"завет\",\"1\"],[\"имя\",\"1\"],[\"объем\",\"2\"],[\"слово\",\"1\"],[\"сказал\",\"1\"]]}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

}