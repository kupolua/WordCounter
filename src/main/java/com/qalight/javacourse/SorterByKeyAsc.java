package com.qalight.javacourse;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by stkotok on 08.08.2014.
 */
public class SorterByKeyAsc {

    public List<Map.Entry<String, Integer>> sortListMapEntry(List<Map.Entry<String, Integer>> entryList) {
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
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
        return entryList;
    }

}
