package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Created by stkotok on 30.07.2014
 */
// todo: rename to TextRefiner (the is no word in English 'Refineder')
// you can use dictionary to check correctness
public class Refineder {

    private static final Logger LOG = LoggerFactory.getLogger(Refineder.class);

    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я]");

    public static String getRefineText(String unrefinedPlainText) {

        return NON_WORD_LETTER_PATTERN.matcher(unrefinedPlainText).replaceAll(" ").toLowerCase();
    }

}
