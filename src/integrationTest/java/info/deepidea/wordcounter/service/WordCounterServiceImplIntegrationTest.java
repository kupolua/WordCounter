package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.controller.CountWordsUserRequest;
import info.deepidea.wordcounter.controller.CountWordsUserRequestImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class WordCounterServiceImplIntegrationTest {
    public static final int DEPTH_OF_CRAWLING = 0;
    public static final boolean INTERNAL_CRAWLING = true;
    private static final String KEY_ASCENDING = "KEY_ASCENDING";
    private static final String TEXT_COUNT = "http://95.158.60.148:8008/kpl/test.rtf";

    @Autowired
    private WordCounterService wordCounterService;

    @Test
    public void testGetWordCounterResult_singleParamNotNullCheck() throws Exception {
        // given
        final CountWordsUserRequest userRequest =
                new CountWordsUserRequestImpl(TEXT_COUNT, DEPTH_OF_CRAWLING, INTERNAL_CRAWLING);

        // when
        final WordCounterResultContainer actualResult = wordCounterService.getWordCounterResult(userRequest);

        // then
        Assert.assertTrue(actualResult != null);
    }

    @Test
    public void testGetWordCounterResult_fullParamNotNullCheck() throws Exception {
        // given
        final String isFilterRequired = "true";
        final CountWordsUserRequest userRequest = new CountWordsUserRequestImpl(
                TEXT_COUNT, KEY_ASCENDING, isFilterRequired, DEPTH_OF_CRAWLING, INTERNAL_CRAWLING);

        // when
        final WordCounterResultContainer actualResult = wordCounterService.getWordCounterResult(userRequest);

        // then
        Assert.assertTrue(actualResult != null);
    }

    @Test
    public void testGetWordCounterResult_singleParam() throws Exception {
        // given
        final String expectedResult = "{a=7, three=3, two=2, one=1}";
        final CountWordsUserRequest userRequest =
                new CountWordsUserRequestImpl(TEXT_COUNT, DEPTH_OF_CRAWLING, INTERNAL_CRAWLING);

        // when
        final WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        final String actualResult = String.valueOf(result.getCountedResult());

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_fullParamSortingCheck() throws Exception {
        // given
        final String expectedResult = "{a=7, one=1, three=3, two=2}";
        final String isFilterRequired = "false";
        final CountWordsUserRequest userRequest = new CountWordsUserRequestImpl(
                TEXT_COUNT, KEY_ASCENDING, isFilterRequired, DEPTH_OF_CRAWLING, INTERNAL_CRAWLING);

        // when
        final WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        final String actualResult = String.valueOf(result.getCountedResult());

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_fullParamFilteringCheck() throws Exception {
        // given
        final String expectedResult = "{one=1, three=3, two=2}";
        final String isFilterRequired = "true";
        final CountWordsUserRequest userRequest = new CountWordsUserRequestImpl(
                TEXT_COUNT, KEY_ASCENDING, isFilterRequired, DEPTH_OF_CRAWLING, INTERNAL_CRAWLING);

        // when
        final WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        final String actualResult = String.valueOf(result.getCountedResult());

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_fullParamNullOrder() throws Exception {
        // given
        final String expectedResult = "{three=3, two=2, one=1}";
        final String isFilterRequired = "true";
        final CountWordsUserRequest userRequest = new CountWordsUserRequestImpl(
                TEXT_COUNT, null, isFilterRequired, DEPTH_OF_CRAWLING, INTERNAL_CRAWLING);

        // when
        final WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);
        final String actualResult = String.valueOf(result.getCountedResult());

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_emptySingleParam()  throws Exception {
        // given
        final String emptyTextCount = "";
        CountWordsUserRequest userRequest =
                new CountWordsUserRequestImpl(emptyTextCount, DEPTH_OF_CRAWLING, INTERNAL_CRAWLING);

        // when
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);

        // then
        // expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_incorrectDepth()  throws Exception {
        // given
        final int incorrectDepth = 3;
        CountWordsUserRequest userRequest =
                new CountWordsUserRequestImpl(TEXT_COUNT, incorrectDepth, INTERNAL_CRAWLING);

        // when
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);

        // then
        // expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_nullSingleParam()  throws Exception {
        // given
        final String emptyTextCount = null;
        CountWordsUserRequest userRequest =
                new CountWordsUserRequestImpl(emptyTextCount, DEPTH_OF_CRAWLING, INTERNAL_CRAWLING);

        // when
        WordCounterResultContainer result = wordCounterService.getWordCounterResult(userRequest);

        // then
        // expected exception
    }
}