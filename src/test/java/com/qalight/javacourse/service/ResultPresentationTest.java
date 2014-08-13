package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kpl on 12.08.2014.
 */
public class ResultPresentationTest  {

    @Test
    public void testCreate() throws Exception {
        //given
        final String sourceLink = "http://www.eslfast.com/supereasy/se/supereasy006.htm";
        final Map<String, Integer> sortedWords = new HashMap<String, Integer>();
            sortedWords.put("Hello", 10);
            sortedWords.put("World", 7);
            sortedWords.put("Word", 13);
            sortedWords.put("Counter", 5);
            sortedWords.put("Project", 24);

        final String expectedResult = "{\"success\":true,\"response\":{\"wordsResult\":{\"http://www.eslfast.com/supereasy/se/supereasy006.htm\":{\"Project\":24,\"Word\":13,\"Counter\":5,\"Hello\":10,\"World\":7}}}}";

        //when
        WordResultCollector wordResultCollector = new WordResultCollector();
        wordResultCollector.setWordResult(sourceLink, sortedWords);

        ResultPresentation resultPresentation = new ResultPresentation();
        String actualResult = resultPresentation.create(wordResultCollector);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }
}
