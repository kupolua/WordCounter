package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.util.UrlFixer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Ignore
public class WordCounterServiceImplIntegrationTest {
    private final static String DATA_TYPE_RESPONSE = "json";
    private final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";

    @Autowired
    private WordCounterService wordCounterService;

    @Test
    public void testGetWordCounterResult_okUrl_KeyAscSort_JsonResp() {
        // given
        final String sortingParam = "KEY_ASCENDING";
        final String expectedResult = "{\"success\":true,\"response\":[[{\"hash\":110183,\"key\":\"one\",\"value\":4},{\"hash\":114,\"key\":\"r\",\"value\":1},{\"hash\":115277,\"key\":\"two\",\"value\":3},{\"hash\":810938485,\"key\":\"ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя\",\"value\":1,\"next\":{\"hash\":34085349,\"key\":\"єнот\",\"value\":1,\"next\":{\"hash\":1025097045,\"key\":\"білка\",\"value\":3}}},{\"hash\":1044630083,\"key\":\"ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя\",\"value\":1},{\"hash\":1025097045,\"key\":\"білка\",\"value\":3},{\"hash\":1036003870,\"key\":\"объем\",\"value\":3},{\"hash\":1036002946,\"key\":\"объём\",\"value\":1,\"next\":{\"hash\":114,\"key\":\"r\",\"value\":1}},{\"hash\":33993926,\"key\":\"ёлка\",\"value\":3},{\"hash\":34085349,\"key\":\"єнот\",\"value\":1,\"next\":{\"hash\":1025097045,\"key\":\"білка\",\"value\":3}},{\"hash\":34168576,\"key\":\"їжак\",\"value\":2}]],\"listUsersUrls\":[\"http://defas.com.ua/java/pageForSeleniumTest.html\"],\"dataTypeResponse\":\"json\"}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_emptyUrl_KeyAscSort_JsonResp() {
        // given
        final String clientRequestEmptyUrl = "";
        final String sortingParam = "KEY_ASCENDING";
        final Exception expectedException = new IllegalArgumentException("Request is null or empty");

        // when
        Exception actualException = null;
        try {
            wordCounterService.getWordCounterResult(clientRequestEmptyUrl, DATA_TYPE_RESPONSE);
        } catch (IllegalArgumentException e) {
            actualException = e;
        }

        // then
        if (actualException != null) {
            Assert.assertEquals(expectedException.toString(), actualException.toString());
        } else {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testGetWordCounterResult_InvalidUrl_KeyAscSort_JsonResp() {
        // given
        final String clientRequestInvalidUrl = "http://95.158.60.148:8008/kpl/testingPageINVALID.html";
        final String sortingParam = "KEY_ASCENDING";
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

    @Test
    public void testGetWordCounterResult_okUrl_KeyDescSort_JsonResp() {
        // given
        final String sortingParam = "KEY_DESCENDING";
        final String expectedResult = "{\"success\":true,\"response\":[[{\"hash\":34168576,\"key\":\"їжак\",\"value\":2},{\"hash\":34085349,\"key\":\"єнот\",\"value\":1,\"next\":{\"hash\":1025097045,\"key\":\"білка\",\"value\":3}},{\"hash\":33993926,\"key\":\"ёлка\",\"value\":3},{\"hash\":1036002946,\"key\":\"объём\",\"value\":1,\"next\":{\"hash\":114,\"key\":\"r\",\"value\":1}},{\"hash\":1036003870,\"key\":\"объем\",\"value\":3},{\"hash\":1025097045,\"key\":\"білка\",\"value\":3},{\"hash\":1044630083,\"key\":\"ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя\",\"value\":1},{\"hash\":810938485,\"key\":\"ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя\",\"value\":1,\"next\":{\"hash\":34085349,\"key\":\"єнот\",\"value\":1,\"next\":{\"hash\":1025097045,\"key\":\"білка\",\"value\":3}}},{\"hash\":115277,\"key\":\"two\",\"value\":3},{\"hash\":114,\"key\":\"r\",\"value\":1},{\"hash\":110183,\"key\":\"one\",\"value\":4}]],\"listUsersUrls\":[\"http://defas.com.ua/java/pageForSeleniumTest.html\"],\"dataTypeResponse\":\"json\"}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_ValAscSort_JsonResp() {
        // given
        final String sortingParam = "VALUE_ASCENDING";
        final String expectedResult = "{\"success\":true,\"response\":[[{\"hash\":1036002946,\"key\":\"объём\",\"value\":1,\"next\":{\"hash\":114,\"key\":\"r\",\"value\":1}},{\"hash\":114,\"key\":\"r\",\"value\":1},{\"hash\":1044630083,\"key\":\"ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя\",\"value\":1},{\"hash\":810938485,\"key\":\"ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя\",\"value\":1,\"next\":{\"hash\":34085349,\"key\":\"єнот\",\"value\":1,\"next\":{\"hash\":1025097045,\"key\":\"білка\",\"value\":3}}},{\"hash\":34085349,\"key\":\"єнот\",\"value\":1,\"next\":{\"hash\":1025097045,\"key\":\"білка\",\"value\":3}},{\"hash\":34168576,\"key\":\"їжак\",\"value\":2},{\"hash\":1025097045,\"key\":\"білка\",\"value\":3},{\"hash\":33993926,\"key\":\"ёлка\",\"value\":3},{\"hash\":115277,\"key\":\"two\",\"value\":3},{\"hash\":1036003870,\"key\":\"объем\",\"value\":3},{\"hash\":110183,\"key\":\"one\",\"value\":4}]],\"listUsersUrls\":[\"http://defas.com.ua/java/pageForSeleniumTest.html\"],\"dataTypeResponse\":\"json\"}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_ValDescSort_JsonResp() {
        // given
        final String sortingParam = "VALUE_DESCENDING";
        final String expectedResult = "{\"success\":true,\"response\":[[{\"hash\":110183,\"key\":\"one\",\"value\":4},{\"hash\":1025097045,\"key\":\"білка\",\"value\":3},{\"hash\":33993926,\"key\":\"ёлка\",\"value\":3},{\"hash\":115277,\"key\":\"two\",\"value\":3},{\"hash\":1036003870,\"key\":\"объем\",\"value\":3},{\"hash\":34168576,\"key\":\"їжак\",\"value\":2},{\"hash\":1036002946,\"key\":\"объём\",\"value\":1,\"next\":{\"hash\":114,\"key\":\"r\",\"value\":1}},{\"hash\":114,\"key\":\"r\",\"value\":1},{\"hash\":1044630083,\"key\":\"ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщььююяя\",\"value\":1},{\"hash\":810938485,\"key\":\"ааббввггддееёёжжззииййккллммннооппррссттууффххццччшшщщъъыыььээююяя\",\"value\":1,\"next\":{\"hash\":34085349,\"key\":\"єнот\",\"value\":1,\"next\":{\"hash\":1025097045,\"key\":\"білка\",\"value\":3}}},{\"hash\":34085349,\"key\":\"єнот\",\"value\":1,\"next\":{\"hash\":1025097045,\"key\":\"білка\",\"value\":3}}]],\"listUsersUrls\":[\"http://defas.com.ua/java/pageForSeleniumTest.html\"],\"dataTypeResponse\":\"json\"}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_InvalidSort_JsonResp() {
        // given
        final String sortingParam = "invalidSortingParam";
        final String expctedExceptionString = "java.lang.IllegalArgumentException: No enum constant com.qalight.javacourse.core.WordResultSorter.invalidSortingParam";

        // when
        Exception actualException = null;
        try {
            wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);
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

    @Test
    public void testGetWordCounterResult_okUrl_KeyAscSort_InvalidResp() {
        // given
        final String sortingParam = "KEY_ASCENDING";
        final String dataTypeResponse = "invalidResponseParam";
        final String expctedExceptionString = "java.lang.NullPointerException";

        // when
        Exception actualException = null;
        try {
            wordCounterService.getWordCounterResult(HTML_TEST_PAGE, dataTypeResponse);
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

    @Configuration
    static class ContextConfiguration {
        @Bean
        public WordCounterService service() {
            return new WordCounterServiceImpl();
        }

        @Bean
        public TextTypeInquirer i() {
            return new TextTypeInquirer();
        }

        @Bean
        public DocumentConverter converter() {
            return new DocumentConverter();
        }

        @Bean
        public WordCounter counter() {
            return new WordCounter();
        }

        @Bean
        ResultPresentationImpl presentation() {
            return new ResultPresentationImpl();
        }

        @Bean
        UrlFixer fixer() {
            return new UrlFixer();
        }
    }
}