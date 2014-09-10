package com.qalight.javacourse.service;

import com.qalight.javacourse.util.TextRefiner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class WordFilterTest {

    private WordFilter wordFilter;
    private TextRefiner refiner;

    @Before
    public void setup() {
        wordFilter = new WordFilter();
        refiner = new TextRefiner();
    }

    @Test
    public void testRemoveUnimportantWords() {
        //given
        String text = "One, the one ONE-oNE  the Two  two to, a two!, three, an three, a усіх и в to і та але";
        List<String> refineText = refiner.refineText(text);

        //when
        List<String> actual = wordFilter.removeUnimportantWords(refineText);

        //then
        List<String> expected = Arrays.asList("one", "one", "one-one", "two", "two", "two", "three", "three", "усіх");
        Assert.assertEquals(expected, actual);
    }
}
