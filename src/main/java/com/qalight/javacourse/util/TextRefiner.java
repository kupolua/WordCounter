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
    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);
    private static final String NON_BREAKING_HYPHEN = "&#8";
    private static final String JSOUP_TAGS = "[<>]";
    private static final String SPACES = "(\\s+)";
    private static final String NON_BREAKING_SPACE = "|(&nbsp;)|(\\xA0)|(&#160;)";
    private static final String SPACE_LENGTH_N = "|(&ensp;)|(&#8194;)";
    private static final String SPACE_LENGTH_M = "|(&emsp;)|(&#8195;)";
    private static final String NARROW_SPACE = "|(&thinsp;)|(&#8201;)";
    private static final String ZERO_WIDTH_NON_JOINER = "|(&zwnj;)|(&#8204;)";

    private static final Pattern WHITESPACES_PATTERN =
            Pattern.compile("(\\s+)|(&nbsp;)|(\\xA0)|(&#160;)|(&ensp;)|(&#8194;)|(&emsp;)|(&#8195;)|(&thinsp;)|(&#8201;)|(&zwnj;)|(&#8204;)|—");
    // todo: use constants with names for values like "&#160;" as reader do not understand what it means
    // I don't know right way to do this:
    //      Pattern.compile(String.join(SPACES).join(NON_BREAKING_SPACE).join(SPACE_LENGTH_N).join(SPACE_LENGTH_M).join(NARROW_SPACE).join(ZERO_WIDTH_NON_JOINER).join("|—"));
    private static final Pattern HYPHEN_PATTERN = Pattern.compile("(.+-)|(-.+)");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))");
    private static final Pattern URL_PATTERN =
            Pattern.compile("(<http://.*)|(<https://.*)|(<ftp://.*)|(<www\\..*)|(http://.*)|(https://.*)|(ftp://.*)|(www\\..*)");
    private static final Pattern CLEAN_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ]");

    public List<String> refineText(String unrefinedPlainText) {
        Assertions.assertStringIsNotNullOrEmpty(unrefinedPlainText, TextRefiner.class);

        List<String> unrefinedWords = asSplitList(unrefinedPlainText);
        List<String> words = new ArrayList<>(unrefinedWords);

        List<String> emailsUrlsList = new ArrayList<String>();
        for (String word : words) {
            Matcher emailMatcher = EMAIL_PATTERN.matcher(word.toLowerCase());
            Matcher urlMatcher = URL_PATTERN.matcher(word.toLowerCase());
            if (urlMatcher.matches() || emailMatcher.matches()) {
                emailsUrlsList.add(word.trim());
            }
        }
        words.removeAll(emailsUrlsList);
        emailsUrlsList = refineUrls(emailsUrlsList);
        words = cleanWords(words);
        words.addAll(emailsUrlsList);
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
}
