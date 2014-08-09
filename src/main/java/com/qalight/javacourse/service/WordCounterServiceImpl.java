package com.qalight.javacourse.service;

import com.google.gson.JsonObject;
import com.qalight.javacourse.CountedWordsGetter;
import com.qalight.javacourse.WordCounterFactory;
import com.qalight.javacourse.util.Assertions;

/**
 * Created by kpl on 30.07.2014.
 */

public class WordCounterServiceImpl implements WordCounterService {
    private final WordCounterFactory wordCounterFactory;

    public WordCounterServiceImpl() {
        wordCounterFactory = new WordCounterFactory();
    }

    @Override
    public String getWordCounterResult(String userUrlsString, String sortingParam, String getTypeStatisticResult) {
        checkParams(userUrlsString, sortingParam, getTypeStatisticResult);

        // todo:
        CountedWordsGetter countedWordsGetter = wordCounterFactory.getTypeRequest(getTypeStatisticResult);


        JsonObject jsonObject = countedWordsGetter.getCountedWords(userUrlsString, sortingParam);
        return jsonObject.toString();
    }

    private static void checkParams(String userUrlsString, String sortingParam, String getTypeStatisticResult) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
        Assertions.assertStringIsNotNullOrEmpty(getTypeStatisticResult);
    }

}