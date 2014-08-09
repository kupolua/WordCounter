package com.qalight.javacourse.service;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by emix on 8/9/14.
 */
public interface WordCounterService {
    String getWordCounterResult (String userUrlsString, String sortingParam, String getTypeStatisticResult);
}
