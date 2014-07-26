package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounterResultSorterTest {

    //todo: This test required finishing

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
        final List<String> expectedResultByKD = new ArrayList<String>() {{
            add("two=2");
            add("three=3");
            add("one=1");
        }};
        final List<String> expectedResultByVA = new ArrayList<String>() {{
            add("one=1");
            add("two=2");
            add("three=3");
        }};
        final List<String> expectedResultByVD = new ArrayList<String>() {{
            add("three=3");
            add("two=2");
            add("one=1");
        }};

        //when
        WordCounterResultSorter resultSorter = new WordCounterResultSorter();
        List<Map.Entry<String, Integer>> actualResultByKA = resultSorter.sortWords(hashMap, "KA");
        List<Map.Entry<String, Integer>> actualResultByKD = resultSorter.sortWords(hashMap, "KD");
        List<Map.Entry<String, Integer>> actualResultByVA = resultSorter.sortWords(hashMap, "VA");
        List<Map.Entry<String, Integer>> actualResultByVD = resultSorter.sortWords(hashMap, "VD");
        List<Map.Entry<String, Integer>> actualResultByBadKey = resultSorter.sortWords(hashMap, "bad key");

        //then
        Assert.assertEquals(expectedResultByKA.toString(), actualResultByKA.toString());
        Assert.assertEquals(expectedResultByKD.toString(), actualResultByKD.toString());
        Assert.assertEquals(expectedResultByVA.toString(), actualResultByVA.toString());
        Assert.assertEquals(expectedResultByVD.toString(), actualResultByVD.toString());
        Assert.assertEquals(actualResultByBadKey.toString(), actualResultByVD.toString());

    }

}