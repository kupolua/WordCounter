package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounterResultSorterTestForKDSortingParam {

    @Test
    public void sortWords() throws Exception {

        //given
        final Map<String, Integer> hashMap = new HashMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};

        final List<String> expectedResultByKD = new ArrayList<String>() {{
            add("two=2");
            add("three=3");
            add("one=1");
        }};

        //when
        WordsSorter resultSorter = new WordsSorter();
        List<Map.Entry<String, Integer>> actualResultByKD = resultSorter.sortWords(hashMap, "KD");

        //then
        Assert.assertEquals(expectedResultByKD.toString(), actualResultByKD.toString());

    }

}