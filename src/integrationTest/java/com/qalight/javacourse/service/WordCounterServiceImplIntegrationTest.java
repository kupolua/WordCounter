package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.util.TextRefiner;
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
        final String expectedResult = "{\"success\":true,\"dataAjax\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"дом\",\"1\"],[\"нообъем\",\"1\"],[\"єнот\",\"1\"],[\"ёлка\",\"2\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"two\",\"3\"],[\"vkamenniygmailcom\",\"1\"],[\"другнарод\",\"1\"],[\"человек\",\"1\"],[\"httphabrahabrrupoststopweekly\",\"1\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"іёлка\",\"1\"],[\"завет\",\"1\"],[\"имя\",\"1\"],[\"httpswwwgooglecomuasearchqjavapatterncompilesplitoqdbeddbcdpatterncompiledbdbdbddbaqschromeijljjsourceidchromeessmieutf-\",\"1\"],[\"объем\",\"2\"],[\"слово\",\"1\"],[\"сказал\",\"1\"]]}";

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

    @Test
    public void testGetWordCounterResult_okUrl_KeyDescSort_JsonResp() {
        // given
        final String expectedResult = "{\"success\":true,\"dataAjax\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"дом\",\"1\"],[\"нообъем\",\"1\"],[\"єнот\",\"1\"],[\"ёлка\",\"2\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"two\",\"3\"],[\"vkamenniygmailcom\",\"1\"],[\"другнарод\",\"1\"],[\"человек\",\"1\"],[\"httphabrahabrrupoststopweekly\",\"1\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"іёлка\",\"1\"],[\"завет\",\"1\"],[\"имя\",\"1\"],[\"httpswwwgooglecomuasearchqjavapatterncompilesplitoqdbeddbcdpatterncompiledbdbdbddbaqschromeijljjsourceidchromeessmieutf-\",\"1\"],[\"объем\",\"2\"],[\"слово\",\"1\"],[\"сказал\",\"1\"]]}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_ValAscSort_JsonResp() {
        // given
        final String expectedResult = "{\"success\":true,\"dataAjax\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"дом\",\"1\"],[\"нообъем\",\"1\"],[\"єнот\",\"1\"],[\"ёлка\",\"2\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"two\",\"3\"],[\"vkamenniygmailcom\",\"1\"],[\"другнарод\",\"1\"],[\"человек\",\"1\"],[\"httphabrahabrrupoststopweekly\",\"1\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"іёлка\",\"1\"],[\"завет\",\"1\"],[\"имя\",\"1\"],[\"httpswwwgooglecomuasearchqjavapatterncompilesplitoqdbeddbcdpatterncompiledbdbdbddbaqschromeijljjsourceidchromeessmieutf-\",\"1\"],[\"объем\",\"2\"],[\"слово\",\"1\"],[\"сказал\",\"1\"]]}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_ValDescSort_JsonResp() {
        // given
        final String expectedResult = "{\"success\":true,\"dataAjax\":[[\"їжак\",\"2\"],[\"объём\",\"1\"],[\"дом\",\"1\"],[\"нообъем\",\"1\"],[\"єнот\",\"1\"],[\"ёлка\",\"2\"],[\"one\",\"4\"],[\"время\",\"1\"],[\"two\",\"3\"],[\"vkamenniygmailcom\",\"1\"],[\"другнарод\",\"1\"],[\"человек\",\"1\"],[\"httphabrahabrrupoststopweekly\",\"1\"],[\"ученики\",\"1\"],[\"білка\",\"3\"],[\"іёлка\",\"1\"],[\"завет\",\"1\"],[\"имя\",\"1\"],[\"httpswwwgooglecomuasearchqjavapatterncompilesplitoqdbeddbcdpatterncompiledbdbdbddbaqschromeijljjsourceidchromeessmieutf-\",\"1\"],[\"объем\",\"2\"],[\"слово\",\"1\"],[\"сказал\",\"1\"]]}";

        // when
        String actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_InvalidSort_JsonResp() {
        // given

        // when
        wordCounterService.getWordCounterResult(HTML_TEST_PAGE, DATA_TYPE_RESPONSE);

        // then
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
        ResultPresentationService presentation() {
            return new ResultPresentationService();
        }

        @Bean
        public TextRefiner getRefiner() {
            return new TextRefiner();
        }

        @Bean
        public WordFilter filter() {
            return new WordFilter();
        }
    }
}