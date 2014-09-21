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
        final WordFilter filter = new WordFilter("the ", "", "");
        final List<String> refinedWords = new ArrayList<>(Arrays.asList("world", "the", "love"));

        // when
        final List<String> actualResult = filter.removeUnimportantWords(refinedWords);

        // then
        String[] expectedResult = {"world", "love"};
        Assert.assertArrayEquals(expectedResult, actualResult.toArray());
    }

    // todo: add other tests
}
