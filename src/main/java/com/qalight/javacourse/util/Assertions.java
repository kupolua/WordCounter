package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class Assertions {

    public static void assertStringIsNotNullOrEmpty(String str) {
        if (str == null || str.trim().length() < 1) {
            throw new IllegalArgumentException("Request is null or empty");
        }
    }

    public static void assertStringIsNotNullOrEmpty(String str, Class classForLog) {
        Logger logger = LoggerFactory.getLogger(classForLog);
        if (str == null || str.trim().length() < 1) {
            logger.error("Request is null or empty");
            throw new IllegalArgumentException("Request is null or empty");
        }
    }

    public static boolean equalCollections(Collection<String> collection, Collection<String> anotherCollection){
        if (collection == null) {
            return (anotherCollection == null);
        }
        if (anotherCollection == collection) {
            return true;
        }
        if (collection.size() != anotherCollection.size()) {
            return false;
        }
        for (String each : collection){
            if (!anotherCollection.contains(each)) {
                return false;
            }
        }
        return true;
    }
}
