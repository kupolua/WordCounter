package com.qalight.javacourse.service;

import com.qalight.javacourse.controller.CountWordsUserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class WordCounterServiceImplIntegrationTest {
    private static final String KEY_ASCENDING = "KEY_ASCENDING";
    private static final String TEXT_COUNT = "http://95.158.60.148:8008/kpl/test.rtf";

    @Autowired
    private WordCounterService wordCounterService;

    @Test
    public void testGetWordCounterResult_singleParamNotNullCheck() throws Exception {
        // given
        CountWordsUserRequest userRequest = new CountWordsUserRequest(TEXT_COUNT);

        // when
        WordCounterResultContainer actualResult = wordCounterService.getWordCounterResult(userRequest);

        // then
        Assert.assertTrue(actualResult != null);
    }

    @Test
    public void testGetWordCounterResult_fullParamNotNullCheck() throws Exception {
        // given
        final String isFilterRequired = "true";
        CountWordsUserRequest userRequest = new CountWordsUserRequest(TEXT_COUNT, KEY_ASCENDING, isFilterRequired);

        // when
        WordCounterResultContainer actualResult = wordCounterService.getWordCounterResult(userRequest);

        // then
        Assert.assertTrue(actualResult != null);
    }

    @Test
    public void testGetWordCounterResult_singleParam() throws Exception {
        // given
        final String expectedResult = "{a=7, three=3, two=2, one=1}";
        CountWordsUserRequest userRequest = new CountWordsUserRequest(TEXT_COUNT);

        // when
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        final String actualResult = String.valueOf(result.getCountedResult());

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_fullParamSortingCheck() throws Exception {
        // given
        final String expectedResult = "{a=7, one=1, three=3, two=2}";
        final String isFilterRequired = "false";
        CountWordsUserRequest userRequest = new CountWordsUserRequest(TEXT_COUNT, KEY_ASCENDING, isFilterRequired);

        // when
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        final String actualResult = String.valueOf(result.getCountedResult());

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_fullParamFilteringCheck() throws Exception {
        // given
        final String expectedResult = "{one=1, three=3, two=2}";
        final String isFilterRequired = "true";
        CountWordsUserRequest userRequest = new CountWordsUserRequest(TEXT_COUNT, KEY_ASCENDING, isFilterRequired);

        // when
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        final String actualResult = String.valueOf(result.getCountedResult());

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_fullParamNullOrder() throws Exception {
        // given
        final String expectedResult = "{three=3, two=2, one=1}";
        final String isFilterRequired = "true";
        CountWordsUserRequest userRequest = new CountWordsUserRequest(TEXT_COUNT, null, isFilterRequired);

        // when
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        final String actualResult = String.valueOf(result.getCountedResult());

        // then
        Assert.assertEquals(expectedResult, actualResult);
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