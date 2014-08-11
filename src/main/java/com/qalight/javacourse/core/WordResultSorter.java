package com.qalight.javacourse.core;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by pavelkulakovsky on 10.08.14.
 */
public class WordResultSorter {

    public Map<String, Integer> keyAscending(Map<String, Integer> countWords) {
        Map<String, Integer> sortedWords = new TreeMap<String, Integer>(countWords);

        return sortedWords;
    }
    public Map<String, Integer> keyDescending(Map<String, Integer> countWords) {
        Map<String, Integer> sortedWords = new TreeMap(Collections.reverseOrder());
        sortedWords.putAll(countWords);

        return sortedWords;
    }
}
