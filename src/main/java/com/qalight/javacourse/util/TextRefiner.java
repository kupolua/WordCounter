package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class TextRefiner {
    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);
    private static final String WHITESPACES_MATCHER = "\\s+";
    private static final String DASH = "—";
    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");
//    private static final Pattern WHITESPACES_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");

    public List<String> refineTextTEST(String unrefinedPlainText) {
        System.out.println("Unrefined text: " + unrefinedPlainText);

        checkIfPlainTextIsNullOrEmpty(unrefinedPlainText);

//        List<String> words = Arrays.asList(unrefinedPlainText.split(WHITESPACES_MATCHER));
        String[] splitWords = unrefinedPlainText.split(WHITESPACES_MATCHER);
        List<String> words = new ArrayList<>();
        for (String s : splitWords) {
            words.add(s);
        }

        List<String> wordsWithoutDash = splitByDashTEST(words);

        System.out.println("  Refined text: " + wordsWithoutDash.toString());
        return wordsWithoutDash;
    }

    private List<String> splitByDashTEST(List<String> words) {
        List<Integer> indexesToCleanWords = new ArrayList<>();
        List<String> allSeparatedWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String potentialDashedWord = words.get(i);
            if (potentialDashedWord.contains(DASH)) {
                String[] separatedWords = potentialDashedWord.split(DASH);
                indexesToCleanWords.add(i);
                for (String separatedWord : separatedWords) {
                    allSeparatedWords.add(separatedWord);
                }
            }
        }
//        words.removeAll(indexesToCleanWords);
        int indLastInd = indexesToCleanWords.size() - 1;
        for (int i = indLastInd; i == 0; i--) {
            words.remove(i);
        }
        for (String word : allSeparatedWords) {
            words.add(word);
        }
        return words;
    }

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
