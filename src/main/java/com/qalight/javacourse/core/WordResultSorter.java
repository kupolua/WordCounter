package com.qalight.javacourse.core;

import java.util.*;

public enum WordResultSorter {
    // todo: use Java 8 approach
//    KEY_ASCENDING {
//        @Override
//        public Map<String, Integer> getSortedWords(Map<String, Integer> countedWords) {
//            Map<String, Integer> sortedWords = new TreeMap<String, Integer>(countedWords);
//            return sortedWords;
//        }
//    },
//
//    KEY_DESCENDING {
//        @Override
//        public Map<String, Integer> getSortedWords(Map<String, Integer> countedWords) {
//            Map<String, Integer> sortedWords = new TreeMap<String, Integer>(Collections.reverseOrder());
//            sortedWords.putAll(countedWords);
//            return sortedWords;
//        }
//    },
//
//    VALUE_ASCENDING {
//        @Override
//        public Map<String, Integer> getSortedWords(Map<String, Integer> countedWords) {
//            List list = new LinkedList<>(countedWords.entrySet());
//            Collections.sort(list, (Object o1, Object o2) -> {
//                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
//            });
//
//            Map sortedWords = new LinkedHashMap();
//            for (Object aList : list) {
//                Map.Entry entry = (Map.Entry) aList;
//                sortedWords.put(entry.getKey(), entry.getValue());
//            }
//            return sortedWords;
//        }
//    },
//
//    VALUE_DESCENDING {
//        @Override
//        public Map<String, Integer> getSortedWords(Map<String, Integer> countedWords) {
//            List list = new LinkedList<>(countedWords.entrySet());
//            Collections.sort(list, (o2, o1) -> {
//                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
//            });
//
//            Map sortedWords = new LinkedHashMap();
//            for (Object aList : list) {
//                Map.Entry entry = (Map.Entry) aList;
//                sortedWords.put(entry.getKey(), entry.getValue());
//            }
//            return sortedWords;
//        }
//    };
//
//    public abstract Map<String, Integer> getSortedWords(Map<String, Integer> countedWords);

    KEY_ASCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(sortedWords, (o1, o2) -> {
                boolean isMapData01Empty = (o1.getKey() == null || o1.getKey().equals(""));
                boolean isMapData02Empty = (o2.getKey() == null || o2.getKey().equals(""));
                if (isMapData01Empty && isMapData02Empty)
                    return 0;
                if (isMapData01Empty)
                    return -1;
                if (isMapData02Empty)
                    return 1;
                return (o1.getKey()).compareTo(o2.getKey());
            });
            return sortedWords;
        }
    },

    KEY_DESCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(sortedWords, (o1, o2) -> {
                boolean isMapData01Empty = (o1.getKey() == null || o1.getKey().equals(""));
                boolean isMapData02Empty = (o2.getKey() == null || o2.getKey().equals(""));
                if (isMapData01Empty && isMapData02Empty)
                    return 0;
                if (isMapData01Empty)
                    return -1;
                if (isMapData02Empty)
                    return 1;
                return (o2.getKey()).compareTo(o1.getKey());
            });
            return sortedWords;
        }
    },

    VALUE_ASCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(sortedWords, (o1, o2) -> {
                boolean isMapData01Empty = (o1.getValue() == null || o1.getValue().equals(""));
                boolean isMapData02Empty = (o2.getValue() == null || o2.getValue().equals(""));
                if (isMapData01Empty && isMapData02Empty)
                    return 0;
                if (isMapData01Empty)
                    return -1;
                if (isMapData02Empty)
                    return 1;
                return (o1.getValue()).compareTo(o2.getValue());
            });
            return sortedWords;
        }
    },

    VALUE_DESCENDING {
        @Override
        public List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords) {
            Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
            Collections.sort(sortedWords, (o1, o2) -> {
                boolean isMapData01Empty = (o1.getValue() == null || o1.getValue().equals(""));
                boolean isMapData02Empty = (o2.getValue() == null || o2.getValue().equals(""));
                if (isMapData01Empty && isMapData02Empty)
                    return 0;
                if (isMapData01Empty)
                    return -1;
                if (isMapData02Empty)
                    return 1;
                return (o2.getValue()).compareTo(o1.getValue());
            });
            return sortedWords;
        }
    };

    public abstract List<Map.Entry<String, Integer>> getSortedWords(Map<String, Integer> countedWords);

}
