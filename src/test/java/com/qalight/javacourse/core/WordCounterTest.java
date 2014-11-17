package com.qalight.javacourse.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class WordCounterTest {
    private WordCounter counter;

    @Before
    public void setup() {
        counter = new WordCounterImpl();
    }

    @Test
    public void testCountWords() {
        // given
        List<String> words = Arrays.asList("one", "one", "one", "one", "two", "two", "two");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("one", 4);
        expected.put("two", 3);

        //when
        Map<String, Integer> actual = counter.countWords(words);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCountWords_empty() {
        // given
        List<String> words = Arrays.asList("");

        Map<String, Integer> expected = new HashMap<>();

        //when
        Map<String, Integer> actual = counter.countWords(words);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCountWords_null() {
        // given
        final List<String> words = null;

        //when
        counter.countWords(words);

        //then
        //expected exception
    }
}
