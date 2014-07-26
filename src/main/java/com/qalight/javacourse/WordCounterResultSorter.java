package com.qalight.javacourse;

import java.util.*;

/**
 * Created by box on 12.06.2014.
 */
//todo: Сделать возможность  сортировки по KA, KD, VA, VD
public class WordCounterResultSorter {

    private String checkSortingParam(String sortingParam) throws Exception {

        if (sortingParam.equals("KA|KD|VA|VD")) {
            return sortingParam;
        } else {
            sortingParam = "KD";
            return sortingParam;
        }
    }

    public List<Map.Entry<String, Integer>> sortWords(
            Map<String, Integer> counter, String sortingParam)  {
        try {
            sortingParam = checkSortingParam(sortingParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

}
