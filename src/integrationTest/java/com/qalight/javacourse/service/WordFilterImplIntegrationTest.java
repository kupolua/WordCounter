package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class WordFilterImplIntegrationTest {

    @Autowired
    private WordFilter wordFilterImpl;

    @Test
    public void testRemoveUnimportantWords_removeWordEn() {
        // given
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
        final Map<String, Integer> actualResult = wordFilterImpl.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRemoveUnimportantWords_removeWordRu() {
        // given
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
        final Map<String, Integer> actualResult = wordFilterImpl.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRemoveUnimportantWords_removeWordUa() {
        // given
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
        final Map<String, Integer> actualResult = wordFilterImpl.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRemoveUnimportantWords_removeVarietyWordsInDifferentLanguages() {
        // given
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
        final Map<String, Integer> actualResult = wordFilterImpl.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRemoveUnimportantWords_removeWordsFromCleanRefinedList() {
        // given
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
        final Map<String, Integer> actualResult = wordFilterImpl.removeUnimportantWords(refinedWords);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUnimportantWords_null() {
        //given
        final Map<String, Integer> countedWords = null;

        //when
        wordFilterImpl.removeUnimportantWords(countedWords);

        //then
        //expected exception
    }
}
