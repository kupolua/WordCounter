package com.qalight.javacourse.core;

import java.util.*;

public enum WordResultSorter {

    //todo stkotok: refactor ENUM with Alex simple example

    //stkotok  Лямбда-выражения -> http://habrahabr.ru/post/216431/

    KEY_ASCENDING {
        @Override
        public Map<String, Integer> getSortedWords(Map<String, Integer> countedWords) {
            Map<String, Integer> sortedWords = new TreeMap<String, Integer>(countedWords);
            return sortedWords;
        }
    },

    KEY_DESCENDING {
        @Override
        public Map<String, Integer> getSortedWords(Map<String, Integer> countedWords) {
            Map<String, Integer> sortedWords = new TreeMap<String, Integer>(Collections.reverseOrder());
            sortedWords.putAll(countedWords);
            return sortedWords;
        }
    },

    VALUE_ASCENDING {
        @Override
        public Map<String, Integer> getSortedWords(Map<String, Integer> countedWords) {
            List list = new LinkedList<>(countedWords.entrySet());
            Collections.sort(list, (Object o1, Object o2) -> {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            });

            Map sortedWords = new LinkedHashMap();
            for (Object aList : list) {
                Map.Entry entry = (Map.Entry) aList;
                sortedWords.put(entry.getKey(), entry.getValue());
            }
            return sortedWords;
        }
    },

    VALUE_DESCENDING {
        @Override
        public Map<String, Integer> getSortedWords(Map<String, Integer> countedWords) {
            List list = new LinkedList<>(countedWords.entrySet());
            Collections.sort(list, (o2, o1) -> {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            });

            Map sortedWords = new LinkedHashMap();
            for (Object aList : list) {
                Map.Entry entry = (Map.Entry) aList;
                sortedWords.put(entry.getKey(), entry.getValue());
            }
            return sortedWords;
        }
    };

    public abstract Map<String, Integer> getSortedWords(Map<String, Integer> countedWords);

}
