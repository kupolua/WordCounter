package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounterResultSorterTestForVASortingParam {

    @Test
    public void sortWords() throws Exception {

        //given
        final Map<String, Integer> hashMap = new HashMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};

        final List<String> expectedResultByVA = new ArrayList<String>() {{
            add("one=1");
            add("two=2");
            add("three=3");
        }};

        //when
        WordsSorter resultSorter = new WordsSorter();
        List<Map.Entry<String, Integer>> actualResultByVA = resultSorter.sortWords(hashMap, "VA");

        //then
        Assert.assertEquals(expectedResultByVA.toString(), actualResultByVA.toString());

    }

}