package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TextRefiner {

    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);

    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я—-]");

    //todo vkamenniy: getRefineText change to getRefinedText: vkamenniy
    public static List<String> getRefineText(String unrefinedPlainText) {

        LOG.debug("Splitting plain text by all whitespace characters.");

        String[] splitWords = unrefinedPlainText.split("\\s+");
        List<String> refinedWords = new ArrayList<>();
        for(String dirtyWord : splitWords){
            String clearWord = NON_WORD_LETTER_PATTERN.matcher(dirtyWord).replaceAll("").toLowerCase();
            refinedWords.add(clearWord);
        }
        return refinedWords;
    }

}
