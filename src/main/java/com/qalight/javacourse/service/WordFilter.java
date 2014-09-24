package com.qalight.javacourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WordFilter {
    private final String wordsForFilter;

    @Autowired
    public WordFilter(
            @Value("${wordsEN}") String wordsEn,
            @Value("${wordsRU}") String wordsRU,
            @Value("${wordsUA}") String wordsUA) {
        wordsForFilter = getWordsForFilter(wordsEn, wordsRU, wordsUA);
    }

    public List<String> removeUnimportantWords(List<String> refinedWords) {
        List<String> filter = Arrays.asList(wordsForFilter.split(" "));
        refinedWords.removeAll(filter);
        return refinedWords;
    }

    private final String getWordsForFilter(String... languages) {
        StringBuilder wordsForFilter = new StringBuilder();
        for (String language : languages){
            wordsForFilter.append(language);
            wordsForFilter.append(" ");
        }
        return wordsForFilter.toString();
    }
}
