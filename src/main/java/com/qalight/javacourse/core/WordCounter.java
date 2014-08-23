package com.qalight.javacourse.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordCounter {

    private static final Logger LOG = LoggerFactory.getLogger(WordCounter.class);

    public Map<String, Integer> countWords(List<String> refinedWords) {
        // todo: check for null

        Map<String, Integer> countedWords = new HashMap<>();

        LOG.debug("Filtering and putting words to HashMap.");
        for (String eachWord : refinedWords) {
            Integer foundValue = countedWords.get(eachWord);
            if (foundValue == null) {
                countedWords.put(eachWord, 1);
            } else {
                Integer newCounter = ++foundValue;
                countedWords.put(eachWord, newCounter);
            }
        }
        LOG.debug("Removing all possible empty entries.");
        countedWords.remove("");

        return countedWords;
    }
}
