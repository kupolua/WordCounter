package com.qalight.javacourse;

import java.net.URISyntaxException;
import java.util.regex.Pattern;

/**
 * Created by box on 12.06.2014...
 */
public class WordFilter {

    private static final Pattern NON_WORD_LETTER_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я]");
    private static final Pattern URL_PATTERN = Pattern.compile("/.*?$");
    private static final Pattern DOT_PATTERN = Pattern.compile("[.]");

    public String filterWord(String word){
        return NON_WORD_LETTER_PATTERN.matcher(word).replaceAll("");
    }

    public String parseUrlForDb(String url) {

        return DOT_PATTERN.matcher(URL_PATTERN.matcher(url).replaceAll("")).replaceAll("_");
//        return URL_PATTERN.matcher(url).replaceAll("_");
    }
}
