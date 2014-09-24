package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class WordFilterIntegrationTest {

    @Autowired
    private WordFilter wordFilter;

    @Test
    public void testRemoveUnimportantWords_removeWordEn() {
        // given
        List<String> refinedWords = new ArrayList<>(Arrays.asList("the", "love", "ocean"));

        // when
        final List<String> actualResult = wordFilter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertArrayEquals(
                Arrays.asList("love" , "ocean").toArray(), actualResult.toArray(new String[actualResult.size()]));
    }

    @Test
     public void testRemoveUnimportantWords_removeWordRu() {
        // given
        List<String> refinedWords = new ArrayList<>(Arrays.asList("мир", "и", "любовь"));

        // when
        final List<String> actualResult = wordFilter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertArrayEquals(
                Arrays.asList("мир", "любовь").toArray(), actualResult.toArray(new String[actualResult.size()]));
    }

    @Test
    public void testRemoveUnimportantWords_removeWordUa() {
        // given
        List<String> refinedWords = new ArrayList<>(Arrays.asList("світ", "і", "любов"));

        // when
        final List<String> actualResult = wordFilter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertArrayEquals(
                Arrays.asList("світ", "любов").toArray(), actualResult.toArray(new String[actualResult.size()]));
    }

    @Test
    public void testRemoveUnimportantWords_removeVarietyWordsInDifferentLanguages() {
        // given
        List<String> refinedWords = new ArrayList<>(
                Arrays.asList("світ", "і", "любов", "мир", "и", "любовь", "world", "the", "love", "він", "їх", "лише", "что", "его", "на", "your", "they", "with"));

        // when
        final List<String> actualResult = wordFilter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertArrayEquals(
                Arrays.asList("світ", "любов", "мир", "любовь", "world", "love").toArray(), actualResult.toArray(new String[actualResult.size()]));
    }

    @Test
    public void testRemoveUnimportantWords_removeWordsFromCleanRefinedList() {
        // given
        List<String> refinedWords = new ArrayList<>(Arrays.asList("світ", "любов", "мир", "любовь", "world", "love"));

        // when
        final List<String> actualResult = wordFilter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertArrayEquals(
                Arrays.asList("світ", "любов", "мир", "любовь", "world", "love").toArray(), actualResult.toArray(new String[actualResult.size()]));
    }
}
