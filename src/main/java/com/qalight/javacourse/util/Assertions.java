package com.qalight.javacourse.util;

import java.util.List;

public class Assertions {
    public static void assertStringIsNotNullOrEmpty(String str) {
        if (str == null || str.trim().length() < 1) {
            throw new IllegalArgumentException("Request is null or empty");
        }
    }

    public static void assertStringIsNotNullOrEmpty(String str, String clientRequest) {
        if (str == null || str.trim().length() < 1) {
            throw new IllegalArgumentException("System cannot count text in the source as it is empty or " +
                    "contains non-readable content or symbols: " + clientRequest);
        }
    }

    public static void assertObjectIsNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object is null");
        }
    }

    public static void assertListIsNotEmpty(List list, String clientRequest){
        if (list.isEmpty()) {
           throw new IllegalArgumentException("System cannot count entered text {" + clientRequest +"}. " +
                   "Did you forget to add 'http://' to the link or entered not readable text?");
        }
    }
}