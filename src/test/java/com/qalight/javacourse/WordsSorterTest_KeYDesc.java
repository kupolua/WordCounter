package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsSorterTest_KeYDesc {

    @Test
    public void testGetSortedWords() throws Exception {

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
        List<Map.Entry<String, Integer>> actualResultByKD = WordsSorter.valueOf("KEY_DESCENDING").getSortedWords(hashMap);

        //then
        Assert.assertEquals(expectedResultByKD.toString(), actualResultByKD.toString());

    }
}