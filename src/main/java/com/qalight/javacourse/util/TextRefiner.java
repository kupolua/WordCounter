package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class TextRefiner {

    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);

    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Z]");

    public static String getRefineText(String unrefinedPlainText) {

        return NON_WORD_LETTER_PATTERN.matcher(unrefinedPlainText).replaceAll(" ").toLowerCase();
    }

}
