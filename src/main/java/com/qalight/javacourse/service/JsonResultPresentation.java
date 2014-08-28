package com.qalight.javacourse.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kpl on 20.08.2014.
 */
public class JsonResultPresentation implements ResultPresentation{
    private static final String DATA_TYPES = "json";
    @Override
    public Boolean isEligible(String dataTypeResponse) {

        boolean isEligible = false;
        if(dataTypeResponse.equals(DATA_TYPES)){
            isEligible = true;
        }
        return isEligible;
    }

    public String createResponse(String textLink, List<Map.Entry<String, Integer>> collectedWordResult, String dataTypeResponse) {
        List<List<Map.Entry<String, Integer>>> countedWordsList = new ArrayList<List<Map.Entry<String, Integer>>>();
        countedWordsList.add(collectedWordResult);

        List<String> urlsList = new ArrayList<>();
        urlsList.add(textLink);

        Gson gson = new Gson();
        JsonObject countedWordsListObj = new JsonObject();

        JsonElement wordResultResponse = gson.toJsonTree(countedWordsList);
        JsonElement listUsersUrls = gson.toJsonTree(urlsList);
        JsonElement dataType = gson.toJsonTree(dataTypeResponse);

        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("response", wordResultResponse);
        countedWordsListObj.add("listUsersUrls", listUsersUrls);
        countedWordsListObj.add("dataTypeResponse", dataType);

        return countedWordsListObj.toString();
    }

    public String createErrorResponse(String errorMessageToUser) {

        Gson gson = new Gson();
        JsonObject errorMessageToUserObj = new JsonObject();

        JsonElement errorMessageToUserJson = gson.toJsonTree(errorMessageToUser);

        errorMessageToUserObj.addProperty("success", false);
        errorMessageToUserObj.add("errorMessageToUser", errorMessageToUserJson);

        return errorMessageToUserObj.toString();
    }
}
