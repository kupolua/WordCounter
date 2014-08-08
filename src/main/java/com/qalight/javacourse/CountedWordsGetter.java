package com.qalight.javacourse;

import com.google.gson.JsonObject;

/**
 * Created by kpl on 08.08.2014.
 */
public interface CountedWordsGetter {

    JsonObject getCountedWords(String userUrlsString, String sortingParam);
}
