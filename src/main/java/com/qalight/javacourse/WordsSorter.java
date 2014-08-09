package com.qalight.javacourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by box on 12.06.2014.
 */

public enum WordsSorter {
    // todo: if parameters are not correct throw exception and catch in servlet class: WordsSorter

    KEY_ASCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            SorterByKeyAsc sorterByKeyAsc = new SorterByKeyAsc();
            sortedWords = sorterByKeyAsc.sortListMapEntry(sortedWords);
            return sortedWords;
        }
    },

    KEY_DESCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            SorterByKeyDesc sorterByKeyDesc = new SorterByKeyDesc();
            sortedWords = sorterByKeyDesc.sortListMapEntry(sortedWords);
            return sortedWords;
        }
    },

    VALUE_ASCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            // todo use comparator classes instead of anonymous comparators
            SorterByValAsc sorterByValAsc = new SorterByValAsc();
            sortedWords = sorterByValAsc.sortListMapEntry(sortedWords);
            return sortedWords;
        }
    },

    VALUE_DESCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            SorterByValDesc sorterByValDesc = new SorterByValDesc();
            sortedWords = sorterByValDesc.sortListMapEntry(sortedWords);
            return sortedWords;
        }
    };

    public abstract List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords);

}
