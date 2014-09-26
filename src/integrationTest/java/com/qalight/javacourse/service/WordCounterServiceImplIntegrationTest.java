package com.qalight.javacourse.service;

import org.junit.Assert;
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

    @Test
    public void testGetWordCounterResult_okUrl_KeyAscSort_JsonResp() throws Exception {
        // given
        final String expectedResult = "{\"success\":true,\"filteredWords\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"<a href=\\\"http://habrahabr.ru/posts/top/weekly/\\\">http://habrahabr.ru/posts/top/weekly/</a>\",\"1\"],[\"ёлка\",\"3\"],[\"two\",\"3\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"имя\",\"1\"],[\"объем\",\"3\"],[\"слово\",\"1\"],[\"<a href=\\\"mailto:vkamenniy@gmail.com\\\">vkamenniy@gmail.com</a>\",\"1\"],[\"дом\",\"1\"],[\"друг\",\"1\"],[\"єнот\",\"1\"],[\"<a href=\\\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\\\">https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8</a>\",\"1\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"человек\",\"1\"],[\"народ\",\"1\"],[\"завет\",\"1\"],[\"сказал\",\"1\"]],\"unFilteredWords\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"<a href=\\\"http://habrahabr.ru/posts/top/weekly/\\\">http://habrahabr.ru/posts/top/weekly/</a>\",\"1\"],[\"ёлка\",\"3\"],[\"two\",\"3\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"і\",\"1\"],[\"але\",\"1\"],[\"имя\",\"1\"],[\"объем\",\"3\"],[\"слово\",\"1\"],[\"a\",\"1\"],[\"но\",\"1\"],[\"<a href=\\\"mailto:vkamenniy@gmail.com\\\">vkamenniy@gmail.com</a>\",\"1\"],[\"дом\",\"1\"],[\"друг\",\"1\"],[\"єнот\",\"1\"],[\"<a href=\\\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\\\">https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8</a>\",\"1\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"та\",\"1\"],[\"the\",\"1\"],[\"человек\",\"1\"],[\"народ\",\"1\"],[\"r\",\"1\"],[\"завет\",\"1\"],[\"сказал\",\"1\"]]}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_emptyUrl_KeyAscSort_JsonResp()  throws Exception {
        // given
        final String clientRequestEmptyUrl = "";

        // when
        wordCounterService.getWordCounterResult(clientRequestEmptyUrl, DATA_TYPE_RESPONSE);

        // then
        // expected exception
    }

    @Test(expected = RuntimeException.class)
    public void testGetWordCounterResult_InvalidUrl_KeyAscSort_JsonResp() throws Exception{
        // given
        final String clientRequestInvalidUrl = "http://95.158.60.148:8008/kpl/testingPageINVALID.html";

        // when
            wordCounterService.getWordCounterResult(clientRequestInvalidUrl, DATA_TYPE_RESPONSE);

        // then
        // expected exception
    }

    @Test
    public void testGetWordCounterResult_okUrl_KeyDescSort_JsonResp() throws Exception{
        // given
        final String expectedResult = "{\"success\":true,\"filteredWords\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"<a href=\\\"http://habrahabr.ru/posts/top/weekly/\\\">http://habrahabr.ru/posts/top/weekly/</a>\",\"1\"],[\"ёлка\",\"3\"],[\"two\",\"3\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"имя\",\"1\"],[\"объем\",\"3\"],[\"слово\",\"1\"],[\"<a href=\\\"mailto:vkamenniy@gmail.com\\\">vkamenniy@gmail.com</a>\",\"1\"],[\"дом\",\"1\"],[\"друг\",\"1\"],[\"єнот\",\"1\"],[\"<a href=\\\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\\\">https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8</a>\",\"1\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"человек\",\"1\"],[\"народ\",\"1\"],[\"завет\",\"1\"],[\"сказал\",\"1\"]],\"unFilteredWords\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"<a href=\\\"http://habrahabr.ru/posts/top/weekly/\\\">http://habrahabr.ru/posts/top/weekly/</a>\",\"1\"],[\"ёлка\",\"3\"],[\"two\",\"3\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"і\",\"1\"],[\"але\",\"1\"],[\"имя\",\"1\"],[\"объем\",\"3\"],[\"слово\",\"1\"],[\"a\",\"1\"],[\"но\",\"1\"],[\"<a href=\\\"mailto:vkamenniy@gmail.com\\\">vkamenniy@gmail.com</a>\",\"1\"],[\"дом\",\"1\"],[\"друг\",\"1\"],[\"єнот\",\"1\"],[\"<a href=\\\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\\\">https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8</a>\",\"1\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"та\",\"1\"],[\"the\",\"1\"],[\"человек\",\"1\"],[\"народ\",\"1\"],[\"r\",\"1\"],[\"завет\",\"1\"],[\"сказал\",\"1\"]]}";
        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_ValAscSort_JsonResp() throws Exception{
        // given
        final String expectedResult = "{\"success\":true,\"filteredWords\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"<a href=\\\"http://habrahabr.ru/posts/top/weekly/\\\">http://habrahabr.ru/posts/top/weekly/</a>\",\"1\"],[\"ёлка\",\"3\"],[\"two\",\"3\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"имя\",\"1\"],[\"объем\",\"3\"],[\"слово\",\"1\"],[\"<a href=\\\"mailto:vkamenniy@gmail.com\\\">vkamenniy@gmail.com</a>\",\"1\"],[\"дом\",\"1\"],[\"друг\",\"1\"],[\"єнот\",\"1\"],[\"<a href=\\\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\\\">https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8</a>\",\"1\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"человек\",\"1\"],[\"народ\",\"1\"],[\"завет\",\"1\"],[\"сказал\",\"1\"]],\"unFilteredWords\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"<a href=\\\"http://habrahabr.ru/posts/top/weekly/\\\">http://habrahabr.ru/posts/top/weekly/</a>\",\"1\"],[\"ёлка\",\"3\"],[\"two\",\"3\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"і\",\"1\"],[\"але\",\"1\"],[\"имя\",\"1\"],[\"объем\",\"3\"],[\"слово\",\"1\"],[\"a\",\"1\"],[\"но\",\"1\"],[\"<a href=\\\"mailto:vkamenniy@gmail.com\\\">vkamenniy@gmail.com</a>\",\"1\"],[\"дом\",\"1\"],[\"друг\",\"1\"],[\"єнот\",\"1\"],[\"<a href=\\\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\\\">https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8</a>\",\"1\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"та\",\"1\"],[\"the\",\"1\"],[\"человек\",\"1\"],[\"народ\",\"1\"],[\"r\",\"1\"],[\"завет\",\"1\"],[\"сказал\",\"1\"]]}";
        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_ValDescSort_JsonResp() throws Exception{
        // given
        final String expectedResult = "{\"success\":true,\"filteredWords\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"<a href=\\\"http://habrahabr.ru/posts/top/weekly/\\\">http://habrahabr.ru/posts/top/weekly/</a>\",\"1\"],[\"ёлка\",\"3\"],[\"two\",\"3\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"имя\",\"1\"],[\"объем\",\"3\"],[\"слово\",\"1\"],[\"<a href=\\\"mailto:vkamenniy@gmail.com\\\">vkamenniy@gmail.com</a>\",\"1\"],[\"дом\",\"1\"],[\"друг\",\"1\"],[\"єнот\",\"1\"],[\"<a href=\\\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\\\">https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8</a>\",\"1\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"человек\",\"1\"],[\"народ\",\"1\"],[\"завет\",\"1\"],[\"сказал\",\"1\"]],\"unFilteredWords\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"<a href=\\\"http://habrahabr.ru/posts/top/weekly/\\\">http://habrahabr.ru/posts/top/weekly/</a>\",\"1\"],[\"ёлка\",\"3\"],[\"two\",\"3\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"і\",\"1\"],[\"але\",\"1\"],[\"имя\",\"1\"],[\"объем\",\"3\"],[\"слово\",\"1\"],[\"a\",\"1\"],[\"но\",\"1\"],[\"<a href=\\\"mailto:vkamenniy@gmail.com\\\">vkamenniy@gmail.com</a>\",\"1\"],[\"дом\",\"1\"],[\"друг\",\"1\"],[\"єнот\",\"1\"],[\"<a href=\\\"https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8\\\">https://www.google.com.ua/search?q=java+pattern+compile+split&oq=%D0%BE%D1%84%D0%BC%D1%84+Pattern.compile+%D1%8B%D0%B7%D0%B4%D1%88%D0%B5+&aqs=chrome.2.69i57j0l2.14141j0j7&sourceid=chrome&es_sm=93&ie=UTF-8</a>\",\"1\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"та\",\"1\"],[\"the\",\"1\"],[\"человек\",\"1\"],[\"народ\",\"1\"],[\"r\",\"1\"],[\"завет\",\"1\"],[\"сказал\",\"1\"]]}";
        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

}