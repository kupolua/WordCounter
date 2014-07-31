package com.qalight.javacourse;

import java.util.*;

/**
 * Created by box on 12.06.2014.
 */

/**
 * This class receive HashMap and String with sorting key with "KA" || "KD" || "VA" || "VD".
 * If sortingParam.equals("Any other string"), sortingParam will be changed to "VD".
 */

public class WordsSorter {

    // todo: use enumaration or  constants sortWords
    // todo: if parameters are not correct throw exception and catch in servlet
    public List<Map.Entry<String, Integer>> sortWords(Map<String, Integer> countedWords, String sortingParam) {

        List<Map.Entry<String, Integer>> sortedWords;
        if (sortingParam.equals("KA")) {
            sortedWords = sortByKA(countedWords);
        } else if (sortingParam.equals("KD")) {
            sortedWords = sortByKD(countedWords);
        } else if (sortingParam.equals("VA")) {
            sortedWords = sortByVA(countedWords);
        } else if (sortingParam.equals("VD")) {
            sortedWords = sortByVD(countedWords);
        } else {
// todo: Add ERROR to Log "Not valid sorting request. Sorting will be done by VD"
// todo: Throw notification to WEB-GUI "Not valid sorting request. Sorting will be done by Value in descending order"
            return sortByVD(countedWords);
        }
        return sortedWords;
    }

    private List<Map.Entry<String, Integer>> sortByKA(Map counter) {
        Set<Map.Entry<String, Integer>> set = counter.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
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
        return list;
    }

    private List<Map.Entry<String, Integer>> sortByKD(Map counter) {
        Set<Map.Entry<String, Integer>> set = counter.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
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
        return list;
    }

    private List<Map.Entry<String, Integer>> sortByVA(Map counter) {
        Set<Map.Entry<String, Integer>> set = counter.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
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
        return list;
    }

    private List<Map.Entry<String, Integer>> sortByVD(Map counter) {
        Set<Map.Entry<String, Integer>> set = counter.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
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
        return list;
    }

}
