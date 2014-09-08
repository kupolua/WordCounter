package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class TextRefiner {
    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);
    private static final String WHITESPACES_MATCHER =
            "(\\s+)|(&nbsp;)|(&#160;)|(&ensp;)|(&#8194;)|(&emsp;)|(&#8195;)|(&thinsp;)|(&#8201;)|(&zwnj;)|(&#8204;)|—";
    private static final String NON_BREAKING_HYPHEN = "&#8";
    private static final String URL_PATTERN = "";
    private static final String EMAIL_PATTERN = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
    private static final Pattern CLEAN_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");

    public List<String> refineText(String unrefinedPlainText) {

        checkIfPlainTextIsNullOrEmpty(unrefinedPlainText);

        List<String> words = new ArrayList<>(Arrays.asList(unrefinedPlainText.split(WHITESPACES_MATCHER)));

        System.out.println(" Splitted text: " + words);

        List<List<String>> listList = separateEmailsAndUrls(words);

        words = replaceSpecSymbols(listList.get(0));

        words = cleanWords(words);

        return words;
    }

    public List<List<String>> separateEmailsAndUrls(List<String> words) {
        List<String> emailsAdnUrls = new ArrayList<>();
        for (String word : words) {
            if (word.contains(EMAIL_PATTERN)) {
                emailsAdnUrls.add(word.trim());
                words.remove(word);
            }
        }
        List<List<String>> listList = new ArrayList<>();
        listList.add(words);
        listList.add(emailsAdnUrls);
        return listList;
    }

    private List<String> replaceSpecSymbols(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (word.contains(NON_BREAKING_HYPHEN)) {
                word = word.replaceAll(NON_BREAKING_HYPHEN, "-");
                words.set(i, word);
            }
        }
        return words;
    }

    private List<String> cleanWords(List<String> words) {
        int i = 0;
        while (i < words.size()) {
            String word = words.get(i);
            word = CLEAN_PATTERN.matcher(word).replaceAll("").toLowerCase();
            if (word.trim().isEmpty()) {
                words.remove(i);
            } else {
                words.set(i, word);
                i++;
            }
        }
        return words;
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
