package com.qalight.javacourse;

import java.util.*;

/**
 * Created by box on 12.06.2014.
 */

public class WordCounterResultSorter {

    private enum SortingParamEnum {KA, KD, VA, VD}

//    private enum SortingParamEnum {
//        KA("KA"),
//        KD("KD"),
//        VA("VA"),
//        VD("VD");
//
//        private String sortingParam;
//
//        SortingParamEnum(String sortingParam) {
//            this.sortingParam = sortingParam;
//        }
//
//        static public SortingParamEnum getType(String pType) throws Exception {
//            for (SortingParamEnum type : SortingParamEnum.values()) {
//                if (type.getSortingParam().equals(pType)) {
//                    return type;
//                }
//            }
//            throw new Exception("unknown type of sortingParam ");
//        }
//
//        public String getSortingParam() {
//            return sortingParam;
//        }
//
//    }

    private String checkSortingParam(String sortingParam) {
//        if (sortingParam.equals("KA|KD|VA|VD")) {
        if (sortingParam.equals("KA")) {

            return sortingParam;
        } else {
            sortingParam = "VD";
// todo: Add ERROR to Log "Not valid sorting request. Sorting will be done by VD"
// todo: Throw notification to WEB-GUI "Not valid sorting request. Sorting will be done by Value in descending order"
            return sortingParam;
        }
    }

    public List<Map.Entry<String, Integer>> sortWords(Map<String, Integer> map, String sortingParam) {
//        System.out.println("unsorted map: " + map);

        String checkedSortingParam = checkSortingParam(sortingParam);
//        System.out.println("checkedSortingParam: " + checkedSortingParam);

        Map<String, Integer> sortedMap = Collections.EMPTY_MAP;

//        Map<String, Integer> map = new HashMap<String, Integer>() {{
//            put("three", 3);
//            put("one", 1);
//            put("two", 2);
//        }};

        switch (SortingParamEnum.valueOf(checkedSortingParam)) {
            case KA:
                sortedMap = sortByKA(map);
                break;
            case KD:
                sortedMap = (Map<String, Integer>) sortByKD(map);
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

        System.out.println("sortedMap by KA: " + sortedMap + "\n\n");


//        Set<Map.Entry<String, Integer>> sortedSetMapEntry = sortedMap.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(sortedMap);
        return list;
    }

    //=================================================================================================================

    private <String extends Comparable, Integer extends Comparable> Map<String, Integer> sortByKA
            (Map<String, Integer> map) {
//    private Map<String, Integer> sortByKA
//        (Map<String, Integer> map){

//        Map<String, Integer> sortedMap = new TreeMap<String, Integer>(map);

        List<String> keys = new LinkedList<String>(map.keySet());

        Collections.sort(keys);

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

        for (String key : keys) {
            sortedMap.put(key, map.get(key));
        }
//        return map;
        return sortedMap;
    }

//    private Map<String, Integer> sortByKA(Map<String, Integer> map) {
//        Map<String, Integer> sortedMap = map;
//        return sortedMap;
//    }

    private Map<String, Integer> sortByKD(Map<String, Integer> map) {

        Map<String, Integer> sortedEntries = new TreeMap<String, Integer>(map);

        return sortedEntries;
    }

//    private Map<String, Integer> sortByKD(Map<String, Integer> map) {
//        Map<String, Integer> sortedMap = map;
//        return sortedMap;
//    }

//    private Map<String, Integer> sortByVA(Map<String, Integer> map) {
//        Map<String, Integer> sortedMap = map;
//        return sortedMap;
//    }

    private <String extends Comparable, Integer extends Comparable> Map<String, Integer> sortByVA
            (Map<String, Integer> map) {

        List<String> keys = new LinkedList<String>(map.keySet());

        Collections.sort(keys);

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

        for (String key : keys) {
            sortedMap.put(key, map.get(key));
        }

        return sortedMap;
    }

    private Map<String, Integer> sortByVD(Map<String, Integer> map) {
        Map<String, Integer> sortedMap = map;
        return sortedMap;
    }

//    public List<Map.Entry<String, Integer>> sortWordsOLD(
//            Map<String, Integer> counter, String sortingParam)  {
//        try {
//            sortingParam = checkSortingParam(sortingParam);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Set<Map.Entry<String, Integer>> set = counter.entrySet();
//        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
//        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//
//                boolean isMapData01Empty = (o1.getValue() == null || o1.getValue().equals(""));
//                boolean isMapData02Empty = (o2.getValue() == null || o2.getValue().equals(""));
//
//                if (isMapData01Empty && isMapData02Empty)
//                    return 0;
//                // at least one of them is not empty
//                if (isMapData01Empty)
//                    return -1;
//                if (isMapData02Empty)
//                    return 1;
//                //none of them is empty
//                    return (o1.getValue()).compareTo(o2.getValue());
//                }
//        });
//        return list;
//    }

}
