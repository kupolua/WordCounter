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
    private final List<String> refinedWords;

    public TextRefiner() {
        refinedWords = new ArrayList<>();
    }

    public void refineText(String unrefinedPlainText) {

        checkIfPlainTextIsNullOrEmpty(unrefinedPlainText);

        LOG.debug("Splitting plain text by all whitespace characters.");

        String[] splitWords = unrefinedPlainText.split(WHITESPACES_MATCHER);

        for (String dirtyWord : splitWords) {
            if (dirtyWord.contains(DASH)) {
                splitByDash(dirtyWord);
            } else {
                String clearWord = refineWord(dirtyWord);
                refinedWords.add(clearWord);
            }
        }
    }

    private String refineWord(String dirtyWord) {
        return NON_WORD_LETTER_PATTERN.matcher(dirtyWord).replaceAll("").toLowerCase();
    }

    private void checkIfPlainTextIsNullOrEmpty(String unrefinedPlainText) {
        if (unrefinedPlainText == null) {
            LOG.warn("\"Text\" is null.");
            throw new NullPointerException("Text is null.");
        }
        if (unrefinedPlainText.equals("")) {
            LOG.warn("\"Text\" is empty.");
            throw new IllegalArgumentException("Text is empty." );
        }
    }

    private void splitByDash(String wordWithDash) {
        String[] separatedWords = wordWithDash.split(DASH);
        for (String undashedWord : separatedWords) {
            String clearWord = refineWord(undashedWord);
            refinedWords.add(clearWord);
        }
    }

    public List<String> getRefinedWords() {
        return refinedWords;
    }
}
