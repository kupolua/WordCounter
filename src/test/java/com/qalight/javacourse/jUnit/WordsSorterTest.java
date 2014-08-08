package com.qalight.javacourse.jUnit;

import com.qalight.javacourse.WordsSorter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsSorterTest {

    @Test
    public void testGetSortedWords() throws Exception {

        for (WordsSorter wordsSorter : WordsSorter.values()) {
            switch (wordsSorter) {
                case KEY_ASCENDING:
                    break;
                case KEY_DESCENDING:
                    break;
                case VALUE_ASCENDING:
                    break;
                case VALUE_DESCENDING:
                    break;
                default:
                    throw new IllegalArgumentException(
                            "WordsSorter enam contain not provided value!\n" +
                                    wordsSorter.toString());
            }
        }

        for (String s : new String[]{"KEY_ASCENDING", "KEY_DESCENDING", "VALUE_ASCENDING", "VALUE_DESCENDING"}) {
            WordsSorter.valueOf(s);
        }

    }

    @Test
    public void testGetSortedWords_KeyAsc() throws Exception {

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
        List<Map.Entry<String, Integer>> actualResultByKA = WordsSorter.valueOf("KEY_ASCENDING").getSortedWords(hashMap);

        //then
        Assert.assertEquals(expectedResultByKA.toString(), actualResultByKA.toString());

    }

    @Test
    public void testGetSortedWords_KeYDesc() throws Exception {

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

    @Test
    public void testGetSortedWords_ValAsc() throws Exception {

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
        List<Map.Entry<String, Integer>> actualResultByVA = WordsSorter.valueOf("VALUE_ASCENDING").getSortedWords(hashMap);

        //then
        Assert.assertEquals(expectedResultByVA.toString(), actualResultByVA.toString());

    }

    @Test
    public void testGetSortedWords_ValDesc() throws Exception {

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
        List<Map.Entry<String, Integer>> actualResultByVD = WordsSorter.valueOf("VALUE_DESCENDING").getSortedWords(hashMap);

        //then
        Assert.assertEquals(expectedResultByVD.toString(), actualResultByVD.toString());

    }

}