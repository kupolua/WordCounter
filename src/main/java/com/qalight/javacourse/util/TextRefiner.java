package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TextRefiner {
    public static final Pattern WHITESPACES_PATTERN =
            // todo: use constants with names for values like "&#160;" as reader do not understand what it means
            Pattern.compile("(\\s+)|(&nbsp;)|(&#160;)|(&ensp;)|(&#8194;)|(&emsp;)|(&#8195;)|(&thinsp;)|(&#8201;)|(&zwnj;)|(&#8204;)|—");
    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);
    private static final String NON_BREAKING_HYPHEN = "&#8";
    private static final Pattern HYPHEN_PATTERN = Pattern.compile("(.+-)|(-.+)");
    private static final Pattern URL_PATTERN = Pattern.compile("(http://.*)|(https://.*)|(ftp://.*)|(www\\..*)");
    private static final Pattern CLEAN_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");

    public List<String> refineText(String unrefinedPlainText) {
        checkIfPlainTextIsNullOrEmpty(unrefinedPlainText);

        List<String> unrefinedWords = asSplitList(unrefinedPlainText);
        List<String> words = new ArrayList<>(unrefinedWords);

        List<String> urlsList = new ArrayList<>();
        for (String word : words) {
            Matcher matcher = URL_PATTERN.matcher(word.toLowerCase());
            if (matcher.matches()) {
                urlsList.add(word.trim());
            }
        }
        words.removeAll(urlsList);
        words = cleanWords(words);
        words.addAll(urlsList);
        return words;
    }

    public static List<String> asSplitList(String unrefinedPlainText) {
        return Arrays.asList(WHITESPACES_PATTERN.split(unrefinedPlainText));
    }

    private List<String> cleanWords(List<String> words) {
        int i = 0;
        while (i < words.size()) {
            String word = words.get(i);

            word = word.toLowerCase();

            if (word.contains(NON_BREAKING_HYPHEN)) {
                word = word.replaceAll(NON_BREAKING_HYPHEN, "-");
                words.set(i, word);
            }

            word = CLEAN_PATTERN.matcher(word).replaceAll("");

            Matcher matcher = HYPHEN_PATTERN.matcher(word);
            if (matcher.matches()) {
                word = word.replaceAll("-", "");
            }

            words.set(i, word);

            if (word.trim().isEmpty() || word.equals("-")) {
                words.remove(i);
            } else {
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
