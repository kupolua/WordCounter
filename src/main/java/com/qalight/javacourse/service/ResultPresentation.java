package com.qalight.javacourse.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by pavelkulakovsky on 10.08.14.
 */
public class ResultPresentation {

    public String create(
            WordResultCollector wordResultCollector
    ) {

        Gson gson = new Gson();
        JsonObject countedWordsListObj = new JsonObject();

        JsonElement wordResultResponse = gson.toJsonTree(wordResultCollector);

        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("response", wordResultResponse);

        return countedWordsListObj.toString();
    }
}
