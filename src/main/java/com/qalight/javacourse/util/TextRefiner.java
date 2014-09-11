package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class TextRefiner {
    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);
    private static final Pattern WHITESPACES_PATTERN = Pattern.compile(
            "(\\s+)|(&nbsp;)|(&#160;)|(&ensp;)|(&#8194;)|(&emsp;)|(&#8195;)|(&thinsp;)|(&#8201;)|(&zwnj;)|(&#8204;)|—");
    private static final String NON_BREAKING_HYPHEN = "&#8";
    private static final Pattern CLEAN_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");

    public List<String> refineText(String unrefinedPlainText) {

        checkIfPlainTextIsNullOrEmpty(unrefinedPlainText);

        List<String> words = new ArrayList<>(Arrays.asList(WHITESPACES_PATTERN.split(unrefinedPlainText)));

        words = cleanWords(words);

        return words;
    }

    private List<String> cleanWords(List<String> words) {
        int i = 0;
        while (i < words.size()) {
            String word = words.get(i);
            if (word.contains(NON_BREAKING_HYPHEN)) {
                word = word.replaceAll(NON_BREAKING_HYPHEN, "-");
                words.set(i, word);
            }
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
