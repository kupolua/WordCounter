package com.qalight.javacourse.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by pavelkulakovsky on 10.08.14.
 */
public class ResultPresentation {

    public String createResponse(Map<String, Map<String, Integer>> collectedWordResult) {

        Gson gson = new Gson();
        JsonObject countedWordsListObj = new JsonObject();

        JsonElement wordResultResponse = gson.toJsonTree(collectedWordResult);

        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("response", wordResultResponse);

        return countedWordsListObj.toString();
    }
}
