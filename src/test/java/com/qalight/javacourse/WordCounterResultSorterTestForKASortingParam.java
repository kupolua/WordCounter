package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounterResultSorterTestForKASortingParam {

    @Test
    public void sortWords() throws Exception {

        //given
        final Map<String, Integer> hashMap = new HashMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};

        final List<String> expectedResultByKA = new ArrayList<String>() {{
            add("one=1");
            add("three=3");
            add("two=2");
        }};

        //when
        WordCounterResultSorter resultSorter = new WordCounterResultSorter();
        List<Map.Entry<String, Integer>> actualResultByKA = resultSorter.sortWords(hashMap, "KA");

        //then
        Assert.assertEquals(expectedResultByKA.toString(), actualResultByKA.toString());

    }

}