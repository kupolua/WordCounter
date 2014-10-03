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

    @Autowired
    private WordCounterService wordCounterService;

    @Test
    public void testGetWordCounterResult_okUrl() throws Exception {
        // given
        final String HTML_TEST_PAGE = "http://defas.com.ua/java/pageForSeleniumTest.html";

        // when
        WordCounterResultContainer actualResult = wordCounterService.getWordCounterResult(HTML_TEST_PAGE);

        // then
        Assert.assertTrue(actualResult != null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_emptyUrl_KeyAscSort_JsonResp()  throws Exception {
        // given
        final String clientRequestEmptyUrl = "";

        // when
        wordCounterService.getWordCounterResult(clientRequestEmptyUrl);

        // then
        // expected exception
    }

    @Test(expected = RuntimeException.class)
    public void testGetWordCounterResult_InvalidUrl_KeyAscSort_JsonResp() throws Exception{
        // given
        final String clientRequestInvalidUrl = "http://95.158.60.148:8008/kpl/testingPageINVALID.html";

        // when
            wordCounterService.getWordCounterResult(clientRequestInvalidUrl);

        // then
        // expected exception
    }
}