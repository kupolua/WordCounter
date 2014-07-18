package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordCounterResultSorterTest {
    @Test
    public void testSortWords() throws Exception {
        WordCounterResultSorter resultSorter = new WordCounterResultSorter();
        //given
        Map<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("two", 2);
        hashMap.put("three", 3);
        hashMap.put("one", 1);
        //when
        List<Map.Entry<String, Integer>> actualResult = resultSorter.sortWords(hashMap);
        //then
        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add("three=3");
        expectedResult.add("two=2");
        expectedResult.add("one=1");
        Assert.assertEquals(expectedResult.toString(), actualResult.toString());
    }

}