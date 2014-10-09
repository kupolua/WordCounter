package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordResultSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class UserTextRequest {
    private static final String FILTERING_IS_ON = "1";
    private static final String WORDS_DESCENDING = "0descending";
    private static final String WORDS_ASCENDING = "0ascending";
    private static final String COUNT_DESCENDING = "1descending";
    private static final String COUNT_ASCENDING = "1ascending";

    @Autowired
    WordFilter wordFilter;

    public Map<String, Integer> getSortedMap(Map<String, Integer> refinedCountedWords, String sortingField, String sortingOrder, String isFilterWords) {
        final String typeOfSort = sortingField + sortingOrder;
        Map<String, Integer> filteredMap = refinedCountedWords;

        //NPE zone
        if (isFilterWords.equals(FILTERING_IS_ON)) {
            filteredMap = getFilteredMap(refinedCountedWords);
        }
        Map<String, Integer> sortedMap;
        switch (typeOfSort) {
            case WORDS_DESCENDING:
                sortedMap = WordResultSorter.KEY_DESCENDING.getSortedWords(filteredMap);
                break;
            case WORDS_ASCENDING:
                sortedMap = WordResultSorter.KEY_ASCENDING.getSortedWords(filteredMap);
                break;
            case COUNT_DESCENDING:
                sortedMap = WordResultSorter.VALUE_DESCENDING.getSortedWords(filteredMap);
                break;
            case COUNT_ASCENDING:
                sortedMap = WordResultSorter.VALUE_ASCENDING.getSortedWords(filteredMap);
                break;
            default:
                sortedMap = refinedCountedWords;
                break;
        }
        return sortedMap;
    }

    private Map<String, Integer> getFilteredMap(Map<String, Integer> refinedCountedWords){
        return wordFilter.removeUnimportantWords(refinedCountedWords);
    }
}
