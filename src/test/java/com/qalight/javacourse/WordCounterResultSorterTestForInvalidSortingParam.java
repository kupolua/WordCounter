package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounterResultSorterTestForInvalidSortingParam {

    @Test
    public void testSortWords() {

        //given
        final Map<String, Integer> hashMap = new HashMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};
        final List<String> expectedResultByVD = new ArrayList<String>() {{
            add("three=3");
            add("two=2");
            add("one=1");
        }};

        //when
        WordsSorter resultSorter = new WordsSorter();
        List<Map.Entry<String, Integer>> actualResultByVD = resultSorter.getSortedWords(hashMap, "VD");
        List<Map.Entry<String, Integer>> actualResultByBadKey = resultSorter.getSortedWords(hashMap, "Invalid sortingParam");

        //then
        Assert.assertEquals(expectedResultByVD.toString(), actualResultByVD.toString());
        Assert.assertEquals(actualResultByBadKey.toString(), actualResultByVD.toString());
    }
}