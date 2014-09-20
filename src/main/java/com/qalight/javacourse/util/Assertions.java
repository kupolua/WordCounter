package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
