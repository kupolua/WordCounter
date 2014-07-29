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

    // todo: make more readable
    public Map<String, Integer> countWords(String plainText){
        WordFilter wordFilter = new WordFilter();
        Map<String, Integer> counter = new HashMap<String, Integer>();
        LOG.debug("Splitting plain text by all whitespace characters.");
        String[] str = plainText.split("\\s+");
        LOG.debug("Filtering and putting words to HashMap.");
        for (String eachWord : str) {
            String filteredWord = wordFilter.filterWord(eachWord);
            Integer foundValue = counter.get(filteredWord);
            if (foundValue == null) {
                counter.put(filteredWord, 1);
            } else {
                Integer newCounter = ++foundValue;
                counter.put(filteredWord, newCounter);
            }
        }
        LOG.debug("Removing all possible empty entries.");
        counter.remove("");
        return counter;
    }



}
