package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsSorterTest_ValDesc {

    @Test
    public void testGetSortedWords() throws Exception {

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
        List<Map.Entry<String, Integer>> actualResultByVD = WordsSorter.valueOf("VALUE_DESCENDING").getSortedWords(hashMap);

        //then
        Assert.assertEquals(expectedResultByVA.toString(), actualResultByVD.toString());

    }
}