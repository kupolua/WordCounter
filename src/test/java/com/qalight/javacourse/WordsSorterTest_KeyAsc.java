package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsSorterTest_KeyAsc {

    @Test
    public void testGetSortedWords() throws Exception {

        //given
        final Map<String, Integer> hashMap = new HashMap<String, Integer>() {{
            put("three11", 3);
            put("one", 1);
            put("two", 2);
        }};

        final List<String> expectedResultByKA = new ArrayList<String>() {{
            add("one=1");
            add("three=3");
            add("two=2");
        }};

        //when
        List<Map.Entry<String, Integer>> actualResultByKA = WordsSorter.valueOf("KEY_ASCENDING").getSortedWords(hashMap);

        //then
        Assert.assertEquals(expectedResultByKA.toString(), actualResultByKA.toString());

    }
}