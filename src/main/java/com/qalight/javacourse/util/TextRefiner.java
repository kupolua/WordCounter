package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class TextRefiner {
    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);
    private static final String WHITESPACES_MATCHER = "\\s+";
    //    private static final String NON_BREAKING_HYPHEN = "[999]";
    private static final String NON_BREAKING_HYPHEN = "&#8";
    private static final String DASH = "—";
    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");
//   private static final Pattern WHITESPACES_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");

    public List<String> refineText(String unrefinedPlainText) {

        checkIfPlainTextIsNullOrEmpty(unrefinedPlainText);

        String[] splitWords = unrefinedPlainText.split(WHITESPACES_MATCHER);
        List<String> words = new ArrayList<>();
        Collections.addAll(words, splitWords);

        List<String> wordsWithoutDash = splitByDash(words);

        List<String> wordsWithNormalHyphen = replaceSpecSymbols(wordsWithoutDash);

        return wordsWithNormalHyphen;
    }

    private List<String> splitByDash(List<String> words) {
        List<String> allDashedWordsToClean = new ArrayList<>();
        List<String> allSeparatedWordsToAdd = new ArrayList<>();
        for (String potentialDashedWord : words) {
            if (potentialDashedWord.contains(DASH)) {
                String[] separatedWords = potentialDashedWord.split(DASH);
                allDashedWordsToClean.add(potentialDashedWord);
                Collections.addAll(allSeparatedWordsToAdd, separatedWords);
            }
        }
        words.removeAll(allDashedWordsToClean);
        words.addAll(allSeparatedWordsToAdd);
        return words;
    }

    public List<String> replaceSpecSymbols(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (word.contains(NON_BREAKING_HYPHEN)) {
                System.out.println("\tunhandled word = " + word);
                word = word.replaceAll(NON_BREAKING_HYPHEN, "-");
                System.out.println("\t  handled word = " + word);
                words.set(i, word);
            }
        }
        return words;
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
            throw new IllegalArgumentException("Text is empty.");
        }
    }

}
