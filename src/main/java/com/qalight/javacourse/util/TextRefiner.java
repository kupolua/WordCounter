package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TextRefiner {
    private static final String DASH = "—";
    private static final String WHITESPACES_MATCHER = "\\s+";
    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);
    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");

    public List<String> refineText(String unrefinedPlainText) {
        checkIfPlainTextIsNullOrEmpty(unrefinedPlainText);

        String[] splitWords = unrefinedPlainText.split(WHITESPACES_MATCHER);

        List<String> refinedWords = new ArrayList<>();

        String clearWord;
        for (String dirtyWord : splitWords) {
            if (dirtyWord.contains(DASH)) {
                clearWord = splitByDash(dirtyWord);
                refinedWords.add(clearWord);
            } else {
                clearWord = refineWord(dirtyWord);
                refinedWords.add(clearWord);
            }
        }
        return refinedWords;
    }

    private String refineWord(String dirtyWord) {
        return NON_WORD_LETTER_PATTERN.matcher(dirtyWord).replaceAll("").toLowerCase();
    }

    private void checkIfPlainTextIsNullOrEmpty(String unrefinedPlainText) {
        if (unrefinedPlainText == null) {
            LOG.warn("\"Text\" is null.");
            throw new IllegalArgumentException("Text is null.");
        }
        if (unrefinedPlainText.equals("")) {
            LOG.warn("\"Text\" is empty.");
            throw new IllegalArgumentException("Text is empty." );
        }
    }

    private String splitByDash(String wordWithDash) {
        String[] separatedWords = wordWithDash.split(DASH);
        String clearWord = null;
        for (String undashedWord : separatedWords) {
            clearWord = refineWord(undashedWord);
        }
        return clearWord;
    }
}
