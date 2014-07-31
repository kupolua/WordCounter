package com.qalight.javacourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Created by stkotok on 30.07.2014
 */
public class UnrefinedTextFilter {

    private static final Logger LOG = LoggerFactory.getLogger(UnrefinedTextFilter.class);

    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я]");

    public String refineText(String unrefinedText) {
        LOG.debug("Filtering words.");
        return NON_WORD_LETTER_PATTERN.matcher(unrefinedText).replaceAll(" ").toLowerCase();
    }

}
