package com.qalight.javacourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by box on 07.06.2014.
 */
public class WordCounter {

    private static final Logger LOG = LoggerFactory.getLogger(WordCounter.class);

    public Map<String, Integer> countWords(String refinedText){
        Map<String, Integer> countedWords = new HashMap<String, Integer>();
        LOG.debug("Splitting plain text by all whitespace characters.");
        String[] str = refinedText.split("\\s+");
        LOG.debug("Filtering and putting words to HashMap.");
        for (String eachWord : str) {
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
