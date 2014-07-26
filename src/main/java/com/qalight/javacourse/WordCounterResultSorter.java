package com.qalight.javacourse;

import java.util.*;

/**
 * Created by box on 12.06.2014.
 */

/**
 * This class receive HashMap and String with sorting key with "KA" || "KD" || "VA" || "VD".
 * If sortingParam.equals("Any other string"), sortingParam will be changed to "VD".
 */

public class WordCounterResultSorter {

    private enum SortingParamEnum {KA, KD, VA, VD}

    private String checkSortingParam(String sortingParam) {
        if (sortingParam.equals("KA") ||
                sortingParam.equals("KD") ||
                sortingParam.equals("VA") ||
                sortingParam.equals("VD")) {
            return sortingParam;
        } else {
            sortingParam = "VD";
// todo: Add ERROR to Log "Not valid sorting request. Sorting will be done by VD"
// todo: Throw notification to WEB-GUI "Not valid sorting request. Sorting will be done by Value in descending order"
            return sortingParam;
        }
    }

    public List<Map.Entry<String, Integer>> sortWords(Map<String, Integer> map, String sortingParam) {
        Map<String, Integer> sortedMap;
        String checkedSortingParam = checkSortingParam(sortingParam);
        switch (SortingParamEnum.valueOf(checkedSortingParam)) {
            case KA:
                sortedMap = sortByKA(map);
                break;
            case KD:
                sortedMap = sortByKD(map);
                break;
            case VA:
                sortedMap = sortByVA(map);
                break;
            case VD:
                sortedMap = sortByVD(map);
                break;
            default:
                sortedMap = sortByVD(map);
                break;
        }
        Map<String, Integer> hashMap = new HashMap<String, Integer>(sortedMap);
        Set<Map.Entry<String, Integer>> sortedSetMapEntry = hashMap.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(sortedSetMapEntry);
        return list;
    }

    private <String extends Comparable, Integer extends Comparable> Map<String, Integer> sortByKA
    (Map<String, Integer> map) {
        Map<String, Integer> sortedMap = new TreeMap<String, Integer>(map);
        return sortedMap;
    }

    private Map<String, Integer> sortByKD(Map<String, Integer> map) {
        Map<String, Integer> sortedEntries = new TreeMap(Collections.reverseOrder());
        sortedEntries.putAll(map);
        return sortedEntries;
    }

    private static <K extends Comparable, V extends Comparable> Map<K, V> sortByVA(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static <K extends Comparable, V extends Comparable> Map<K, V> sortByVD(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}
