package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class WordCounterTest {

    @Test
    public void testCountWords() throws Exception {

        // given
        final String text = "one two two three three three";
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("one", 1);
            put("two", 2);
            put("three", 3);
        }};

        // when
        WordCounter wordCounter = new WordCounter();
        final Map<String, Integer> actualResult = wordCounter.countWords(text);

        // then
        Assert.assertEquals(expectedResult, actualResult);

    }
}