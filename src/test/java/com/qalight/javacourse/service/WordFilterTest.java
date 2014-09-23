package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordFilterTest {

    @Test
    public void removeUnimportantWords_removeOneWordEng() {
        // given
        final WordFilter filter = new WordFilter("the", "", "");
        final List<String> refinedWords = new ArrayList<>(Arrays.asList("world", "the", "love"));

        // when
        final List<String> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        String[] expectedResult = {"world", "love"};
        Assert.assertArrayEquals(expectedResult, actualResult.toArray());
    }

    @Test
    public void removeUnimportantWords_removeOneWordRu() {
        // given
        final WordFilter filter = new WordFilter("", "и", "");
        final List<String> refinedWords = new ArrayList<>(Arrays.asList("мир", "и", "любовь"));

        // when
        final List<String> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        String[] expectedResult = {"мир", "любовь"};
        Assert.assertArrayEquals(expectedResult, actualResult.toArray());
    }

    @Test
    public void removeUnimportantWords_removeOneWordUa() {
        // given
        final WordFilter filter = new WordFilter("", "", "і");
        final List<String> refinedWords = new ArrayList<>(Arrays.asList("світ", "і", "любов"));

        // when
        final List<String> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        String[] expectedResult = {"світ", "любов"};
        Assert.assertArrayEquals(expectedResult, actualResult.toArray());
    }

    @Test
    public void removeUnimportantWords_removeWordsInDifferentLanguages() {
        // given
        final WordFilter filter = new WordFilter("the", "и", "і");
        final List<String> refinedWords = new ArrayList<>(Arrays.asList("світ", "і", "любов", "мир", "и", "любовь", "world", "the", "love"));

        // when
        final List<String> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        String[] expectedResult = {"світ", "любов", "мир", "любовь", "world", "love"};
        Assert.assertArrayEquals(expectedResult, actualResult.toArray());
    }

    @Test
    public void removeUnimportantWords_removeVarietyWordsInDifferentLanguages() {
        // given
        final WordFilter filter = new WordFilter("the your they with", "и что его на", "і він їх лише");
        final List<String> refinedWords = new ArrayList<>(
                Arrays.asList("світ", "і", "любов", "мир", "и", "любовь", "world", "the", "love", "він", "їх", "лише", "что", "его", "на", "your", "they", "with"));

        // when
        final List<String> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        String[] expectedResult = {"світ", "любов", "мир", "любовь", "world", "love"};
        Assert.assertArrayEquals(expectedResult, actualResult.toArray());
    }

    @Test
    public void removeUnimportantWords_removeWordsFromCleanRefinedList() {
        // given
        final WordFilter filter = new WordFilter("the your they with", "и что его на", "і він їх лише");
        final List<String> refinedWords = new ArrayList<>(Arrays.asList("світ", "любов", "мир", "любовь", "world", "love"));

        // when
        final List<String> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        String[] expectedResult = {"світ", "любов", "мир", "любовь", "world", "love"};
        Assert.assertArrayEquals(expectedResult, actualResult.toArray());
    }
}
