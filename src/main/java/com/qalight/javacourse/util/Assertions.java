package com.qalight.javacourse.util;

import java.util.Collection;

public class Assertions {

    public static void assertStringIsNotNullOrEmpty(String str) {
        if (str == null || str.trim().length() < 1) {
            throw new IllegalArgumentException("Request is null or empty");
        }
    }

    public static void assertObjectIsNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object is null");
        }
    }

    public static boolean equalCollections(Collection<?> collection, Collection<?> anotherCollection){
        if (collection == null) {
            return (anotherCollection == null);
        }
        if (anotherCollection == collection) {
            return true;
        }
        if (collection.size() != anotherCollection.size()) {
            return false;
        }
        for (Object each : collection){
            if (!anotherCollection.contains(each)) {
                return false;
            }
        }
        return true;
    }
}
