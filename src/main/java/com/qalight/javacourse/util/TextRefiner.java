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

    public List<String> refineText(String clientRequest) {
        Assertions.assertStringIsNotNullOrEmpty(clientRequest);

        List<String> unrefinedWords = asSplitList(clientRequest);
        List<String> words = new ArrayList<>(unrefinedWords);

        List<String> emailsUrlsList = new ArrayList<>();
        for (String word : words) {
            Matcher emailMatcher = EMAIL_PATTERN.matcher(word.toLowerCase());
            Matcher urlMatcher = URL_PATTERN.matcher(word.toLowerCase());
            if (urlMatcher.matches() || emailMatcher.matches()) {
                emailsUrlsList.add(word.trim());
            }
        }
        words.removeAll(emailsUrlsList);
//        emailsUrlsList = refineUrls(emailsUrlsList); //todo: WORDS-314 If the text has a long link or word, the first column of the table is strongly stretched wide
        words = cleanWords(words);
//        words.addAll(emailsUrlsList); //todo: WORDS-314 If the text has a long link or word, the first column of the table is strongly stretched wide
        Assertions.assertListIsNotEmpty(words, clientRequest);
        LOG.debug("Text is refined.");
        return words;
    }

    private static List<String> asSplitList(String unrefinedPlainText) {
        return Arrays.asList(WHITESPACES_PATTERN.split(unrefinedPlainText));
    }

    private List<String> refineUrls(List<String> emailsUrlsList) {
        for (int i = 0; i < emailsUrlsList.size(); i++) {
            String emailOrUrl = emailsUrlsList.get(i);
            String cleanUrl = emailOrUrl;

            if (emailOrUrl.contains("<") || emailOrUrl.contains(">")) {
                cleanUrl = emailOrUrl.replaceAll(JSOUP_TAGS, "");
            }

            Matcher urlMatcher = URL_PATTERN.matcher(emailOrUrl.toLowerCase());
            String refinedEmailOrUrl = null;
            if (urlMatcher.matches()) {
                refinedEmailOrUrl = "<a href=\"" + cleanUrl + "\">" + cleanUrl + "</a>";
            }

            Matcher emailMatcher = EMAIL_PATTERN.matcher(emailOrUrl.toLowerCase());
            if (emailMatcher.matches()) {
                refinedEmailOrUrl = "<a href=\"mailto:" + cleanUrl + "\">" + cleanUrl + "</a>";
            }

            emailsUrlsList.set(i, refinedEmailOrUrl);
        }
        return emailsUrlsList;
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
