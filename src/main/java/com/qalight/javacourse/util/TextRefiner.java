package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TextRefiner {

    private static final Logger LOG = LoggerFactory.getLogger(TextRefiner.class);

    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Z-]");

    public List<String> getRefinedText(String unrefinedPlainText) {

        if (unrefinedPlainText == null) {
            throw new IllegalArgumentException("UnrefinedPlainText is null");
        }
        LOG.debug("Splitting plain text by all whitespace characters.");
        String[] splitWords = unrefinedPlainText.split("\\s+");
        List<String> refinedWords = new ArrayList<>();
        for(String dirtyWord : splitWords){
            if(dirtyWord.contains("—")){
                String[] splitByDash = dirtyWord.split("—");
                for(String undashedWord : splitByDash){
                    String clearWord = refineWord(undashedWord);
                    refinedWords.add(clearWord);
                }
            } else {
                String clearWord = refineWord(dirtyWord);
                refinedWords.add(clearWord);
            }
        }
        return refinedWords;
    }

    private String refineWord(String dirtyWord){
        return NON_WORD_LETTER_PATTERN.matcher(dirtyWord).replaceAll("").toLowerCase();
    }

}
