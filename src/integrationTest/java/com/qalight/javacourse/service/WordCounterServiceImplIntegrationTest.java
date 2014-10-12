package com.qalight.javacourse.service;

import com.qalight.javacourse.controller.CountWordsUserRequest;
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
    private static final String TEXT_COUNT = "http://defas.com.ua/java/pageForSeleniumTest.html";

    @Autowired
    private WordCounterService wordCounterService;

    //todo: create additional tests, use mock?

    @Test
    public void testGetWordCounterResult_singleParam() throws Exception {
        // given
        CountWordsUserRequest userRequest = new CountWordsUserRequest(TEXT_COUNT);

        // when
        WordCounterResultContainer actualResult = wordCounterService.getWordCounterResult(userRequest);

        // then
        Assert.assertTrue(actualResult != null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_emptySingleParam()  throws Exception {
        // given
        final String emptyTextCount = "";
        CountWordsUserRequest userRequest = new CountWordsUserRequest(emptyTextCount);

        // when
        wordCounterService.getWordCounterResult(userRequest);

        // then
        // expected exception
    }

    @Test(expected = RuntimeException.class)
    public void testGetWordCounterResult_invalidSingleParam() throws Exception{
        // given
        final String invalidTextCount = "http://95.158.60.148:8008/kpl/testingPageINVALID.html";
        CountWordsUserRequest userRequest = new CountWordsUserRequest(invalidTextCount);

        // when
            wordCounterService.getWordCounterResult(userRequest);

        // then
        // expected exception
    }
}