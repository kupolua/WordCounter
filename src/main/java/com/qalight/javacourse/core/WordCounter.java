package com.qalight.javacourse.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by box on 07.06.2014
 */
public class WordCounter {

    private static final Logger LOG = LoggerFactory.getLogger(WordCounter.class);

    public Map<String, Integer> countWords(String refinedPlainText) {

        Map<String, Integer> countedWords = new HashMap<String, Integer>();
        countedWords.put("Hello", 10);
        countedWords.put("World", 7);
        countedWords.put("Word", 13);
        countedWords.put("Counter", 5);
        countedWords.put("Project", 24);

        return countedWords;
    }


}
