package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class WordFilterTest {

    @Test
    public void removeUnimportantWords_removeOneWordEng() {
        // given
        final WordFilter filter = new WordFilter("the", "", "");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("world", 1);
            put("the", 1);
            put("love", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("world", 1);
            put("love", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeOneWordRu() {
        // given
        final WordFilter filter = new WordFilter("", "и", "");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("мир", 1);
            put("и", 1);
            put("любовь", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("мир", 1);
            put("любовь", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeOneWordUa() {
        // given
        final WordFilter filter = new WordFilter("", "", "і");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("і", 1);
            put("любов", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeWordsInDifferentLanguages() {
        // given
        final WordFilter filter = new WordFilter("the", "и", "і");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("і", 1);
            put("любов", 1);
            put("мир", 1);
            put("и", 1);
            put("любовь", 1);
            put("world", 1);
            put("the", 1);
            put("love", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
            put("мир", 1);
            put("любовь", 1);
            put("world", 1);
            put("love", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeVarietyWordsInDifferentLanguages() {
        // given
        final WordFilter filter = new WordFilter("the your they with", "и что его на", "і він їх лише");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("і", 1);
            put("любов", 1);
            put("мир", 1);
            put("и", 1);
            put("любовь", 1);
            put("world", 1);
            put("the", 1);
            put("love", 1);
            put("він", 1);
            put("їх", 1);
            put("лише", 1);
            put("что", 1);
            put("его", 1);
            put("на", 1);
            put("your", 1);
            put("they", 1);
            put("with", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
            put("мир", 1);
            put("любовь", 1);
            put("world", 1);
            put("love", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeWordsFromCleanRefinedList() {
        // given
        final WordFilter filter = new WordFilter("the your they with", "и что его на", "і він їх лише");
//        final List<String> refinedWords = new ArrayList<>(Arrays.asList("світ", "любов", "мир", "любовь", "world", "love"));
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
            put("мир", 1);
            put("любовь", 1);
            put("world", 1);
            put("love", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
            put("мир", 1);
            put("любовь", 1);
            put("world", 1);
            put("love", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }
}