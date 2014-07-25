package com.qalight.javacourse;

import java.util.*;

/**
 * Created by box on 12.06.2014.
 */
//todo: Сделать возможность сортировки по KA, KD, VA, VD
public class WordCounterResultSorter {

    private enum SortingParamEnum {
        KA("KA"),
        KD("KD"),
        VA("VA"),
        VD("VD");

        private String sortingParam;

        SortingParamEnum(String sortingParam) {
            this.sortingParam = sortingParam;
        }

        static public SortingParamEnum getType(String pType) throws Exception {
            for (SortingParamEnum type : SortingParamEnum.values()) {
                if (type.getSortingParam().equals(pType)) {
                    return type;
                }
            }
            throw new Exception("unknown type of sortingParam ");
        }

        public String getSortingParam() {
            return sortingParam;
        }

    }

    private String checkSortingParam(String sortingParam) {
        if (sortingParam.equals("KA|KD|VA|VD")) {
            return sortingParam;
        } else {
            sortingParam = "VD";
// todo: Add ERROR to Log "Not valid sorting request. Sorting will be done by VD"
// todo: Throw notification to WEB-GUI "Not valid sorting request. Sorting will be done by Value in descending order"
            return sortingParam;
        }
    }

    public List<Map.Entry<String, Integer>> sortWords(Map<String, Integer> map, String sortingParam) {

        sortingParam = checkSortingParam(sortingParam);

        switch (SortingParamEnum.valueOf(sortingParam)) {
            case KA:
                System.out.println("SortingParamEnum.KA");
                break;

            case KD:
                System.out.println("SortingParamEnum.KD");
                break;

            case VA:
                System.out.println("SortingParamEnum.VA");
                break;

            case VD:
                System.out.println("SortingParamEnum.VD");
                break;

            default:
// todo: Add ERROR to Log "Not valid sorting request. Sorting will be done by VD"
// todo: Throw notification to WEB-GUI "Not valid sorting request. Sorting will be done by Value in descending order"
                System.out.println("SortingParamEnum.VD");
                // sort by SortingParamEnum.VD;
                break;
        }

        // todo: Add sortByKA
        // todo: Add sortByKD
        // todo: Add sortByVA
        // todo: Add sortByVD

        Map<String, Integer> sortedMap = Collections.EMPTY_MAP;
        Set<Map.Entry<String, Integer>> sortedSetMapEntry = sortedMap.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(sortedSetMapEntry);

        return list;
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
