package com.qalight.javacourse.util;

import java.util.List;

import static com.qalight.javacourse.util.ErrorCodeImpl.*;

public class Assertions {
    public static void assertStringIsNotNullOrEmpty(String str) {
        if (str == null || str.trim().length() < 1) {
            throw new WordCounterArgumentException(REQUEST_IS_EMPTY_OR_NULL);
        }
    }

    public static void assertStringIsNotNullOrEmpty(String str, String clientRequest) {
        if (str == null || str.trim().length() < 1) {
            throw new WordCounterArgumentException(SOURCE_IS_EMPTY_OR_NON_READABLE, clientRequest);
        }
    }

    public static void assertObjectIsNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object is null");
        }
    }

    public static void assertListIsNotEmpty(List list, String clientRequest){
        if (list.isEmpty()) {
           throw new WordCounterArgumentException(CANNOT_COUNT_TEXT);
        }
    }
}