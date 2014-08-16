package com.qalight.javacourse.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pavelkulakovsky on 10.08.14.
 */
// todo: create different types of Presentation (JSon, plain text, HTML)
public class ResultPresentation {

    public JsonObject createResponse(String textLink, List<Map.Entry<String, Integer>> collectedWordResult) {
        List<List<Map.Entry<String, Integer>>> countedWordsList = new ArrayList<List<Map.Entry<String, Integer>>>();
        countedWordsList.add(collectedWordResult);

        List<String> urlsList = new ArrayList<>();
        urlsList.add(textLink);

        Gson gson = new Gson();
        JsonObject countedWordsListObj = new JsonObject();

        JsonElement wordResultResponse = gson.toJsonTree(countedWordsList);
        JsonElement listUsersUrls = gson.toJsonTree(urlsList);

        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("response", wordResultResponse);
        countedWordsListObj.add("listUsersUrls", listUsersUrls);

        return countedWordsListObj;
    }

    public void createErrorResponse(String errorMessageToUser) {

        Gson gson = new Gson();
        JsonObject errorMessageToUserObj = new JsonObject();

        JsonElement errorMessageToUserJson = gson.toJsonTree(errorMessageToUser);

        errorMessageToUserObj.addProperty("success", false);
        errorMessageToUserObj.add("errorMessageToUser", errorMessageToUserJson);

        String errorMessageToUserString = errorMessageToUserObj.toString();

        WordCounterServiceImpl.errorMessageToUser = errorMessageToUserString;
    }
}
