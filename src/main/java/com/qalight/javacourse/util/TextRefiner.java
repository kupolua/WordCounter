package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import static com.qalight.javacourse.util.TextRefinerConstants.*;

@Component
public class TextRefiner {
    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);

    public List<String> refineText(String unrefinedPlainText) {
        Assertions.assertStringIsNotNullOrEmpty(unrefinedPlainText);

        List<String> words = asSplitList(unrefinedPlainText);

        words = removeUrlsAndEmails(words);

        words = cleanWords(words);

        Assertions.assertListIsNotEmpty(words, unrefinedPlainText);
        LOG.debug("Text is refined.");
        return words;
    }

    private List<String> removeUrlsAndEmails(List<String> words) {
        List<String> emailsUrlsList = new ArrayList<>();
        for (String word : words) {
            Matcher emailMatcher = EMAIL_PATTERN.matcher(word.toLowerCase());
            Matcher urlMatcher = URL_PATTERN.matcher(word.toLowerCase());
            if (urlMatcher.matches() || emailMatcher.matches()) {
                emailsUrlsList.add(word.trim());
            }
        }
        words.removeAll(emailsUrlsList);
        return words;
    }

    private static List<String> asSplitList(String unrefinedPlainText) {
        List<String> lockedWords = Arrays.asList(WHITESPACES_PATTERN.split(unrefinedPlainText));
        return new ArrayList<>(lockedWords);
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

            Matcher matcher = HYPHEN_AND_APOSROFE_PATTERN.matcher(word);
            if (matcher.matches()) {
                word = word.replaceAll("-", "");
                word = word.replaceAll("'", "");
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
}
