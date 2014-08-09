package com.qalight.javacourse.core;

import java.util.*;

/**
 * Created by box on 12.06.2014.
 */

public enum WordsSorter {
    // todo stkotok: if parameters are not correct throw exception and catch in servlet class: WordsSorter

    KEY_ASCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(sortedWords, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                    boolean isMapData01Empty = (o1.getKey() == null || o1.getKey().equals(""));
                    boolean isMapData02Empty = (o2.getKey() == null || o2.getKey().equals(""));

                    if (isMapData01Empty && isMapData02Empty)
                        return 0;
                    // at least one of them is not empty
                    if (isMapData01Empty)
                        return -1;
                    if (isMapData02Empty)
                        return 1;
                    //none of them is empty
                    return (o1.getKey()).compareTo(o2.getKey());
                }
            });
            return sortedWords;
        }
    },

    KEY_DESCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(sortedWords, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                    boolean isMapData01Empty = (o1.getKey() == null || o1.getKey().equals(""));
                    boolean isMapData02Empty = (o2.getKey() == null || o2.getKey().equals(""));

                    if (isMapData01Empty && isMapData02Empty)
                        return 0;
                    // at least one of them is not empty
                    if (isMapData01Empty)
                        return -1;
                    if (isMapData02Empty)
                        return 1;
                    //none of them is empty
                    return (o2.getKey()).compareTo(o1.getKey());
                }
            });
            return sortedWords;
        }
    },

    VALUE_ASCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            // todo stkotok: use comparator classes instead of anonymous comparators
            Collections.sort(sortedWords, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                    boolean isMapData01Empty = (o1.getValue() == null || o1.getValue().equals(""));
                    boolean isMapData02Empty = (o2.getValue() == null || o2.getValue().equals(""));

                    if (isMapData01Empty && isMapData02Empty)
                        return 0;
                    // at least one of them is not empty
                    if (isMapData01Empty)
                        return -1;
                    if (isMapData02Empty)
                        return 1;
                    //none of them is empty
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });
            return sortedWords;
        }
    },

    VALUE_DESCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(sortedWords, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                    boolean isMapData01Empty = (o1.getValue() == null || o1.getValue().equals(""));
                    boolean isMapData02Empty = (o2.getValue() == null || o2.getValue().equals(""));

                    if (isMapData01Empty && isMapData02Empty)
                        return 0;
                    // at least one of them is not empty
                    if (isMapData01Empty)
                        return -1;
                    if (isMapData02Empty)
                        return 1;
                    //none of them is empty
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });
            return sortedWords;
        }
    };

    public abstract List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords);

}
