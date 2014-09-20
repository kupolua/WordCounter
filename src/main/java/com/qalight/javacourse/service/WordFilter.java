package com.qalight.javacourse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class WordFilter {
    @Value("${wordsEN}")
    private String wordsEn;
    @Value("${wordsRU}")
    private String wordsRU;
    @Value("${wordsUA}")
    private String wordsUA;

    public List<String> removeUnimportantWords(List<String> refinedWords) {
        String wordsForFilter = getWordsForFilter();
        List<String> filter = Arrays.asList(wordsForFilter.split(" "));
        refinedWords.removeAll(filter);
        return refinedWords;
    }

    public String getWordsForFilter() {
        StringBuilder wordsForFilter = new StringBuilder();
        wordsForFilter.append(wordsEn);
        wordsForFilter.append(" ");
        wordsForFilter.append(wordsRU);
        wordsForFilter.append(" ");
        wordsForFilter.append(wordsUA);
        return wordsForFilter.toString();
    }
}
