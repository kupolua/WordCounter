package com.qalight.javacourse;

import java.util.*;

/**
 * Created by box on 12.06.2014.
 */
//todo: Сделать возможность сортировки по KA, KD, VA, VD
public class WordCounterResultSorter {

    private String checkSortingParam(String sortingParam) {

        if (sortingParam.equals("KA|KD|VA|VD")) {
            return sortingParam;
        } else {
            try {
                throw new Exception("sortingParam is not valid");
            } catch (Exception e) {
                e.printStackTrace();
            }
            sortingParam = "KD";
            System.out.println(sortingParam);
        }

        return sortingParam;
    }

    public Map<String, Integer> sortWords(
            Map<String, Integer> counter, String sortingParam) {
        sortingParam = checkSortingParam(sortingParam);
        Set<Map.Entry<String, Integer>> set = counter.entrySet();
        System.out.println(set);
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
//                if (sortByKV) {
                    return (o1.getValue()).compareTo(o2.getValue());
//                } else {
//                    return (o2.getValue()).compareTo(o1.getValue());
                }
//            }
        });
        Map<String, Integer> sortedMap;
        sortedMap = (Map<String, Integer>) list;
        return sortedMap;
    }

}
