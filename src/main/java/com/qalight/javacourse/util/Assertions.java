package com.qalight.javacourse.util;

public class Assertions {

    public static void assertStringIsNotNullOrEmpty (String str){
        if (str == null || str.trim().length() < 1 ) {
            throw new IllegalArgumentException("str is null or empty");
        }
    }
}
