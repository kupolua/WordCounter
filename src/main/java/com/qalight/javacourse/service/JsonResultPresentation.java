package com.qalight.javacourse.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonResultPresentation implements ResultPresentation {
    // todo: add checks for nulls and other validations for methods
    private static final String DATA_TYPES = "json";

    @Override
    public boolean isEligible(String dataTypeResponse) {
        boolean isEligible = false;
        if (dataTypeResponse.equals(DATA_TYPES)) {
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public String createResponse(Map<String, Integer> unRefinedCountedWords, Map<String, Integer> refinedCountedWords) {
        List<List<String>> filteredCountedWords = new ArrayList<>();
        List<List<String>> unFilteredCountedWords = new ArrayList<>();

        for (Map.Entry<String, Integer> entryUnFilteredWords : unRefinedCountedWords.entrySet()) {
            List<String> countUnFilteredWordsWordResultResponse = new ArrayList<>();
            countUnFilteredWordsWordResultResponse.add(entryUnFilteredWords.getKey());
            countUnFilteredWordsWordResultResponse.add(entryUnFilteredWords.getValue().toString());
            unFilteredCountedWords.add(countUnFilteredWordsWordResultResponse);
        }
        for (Map.Entry<String, Integer> entry : refinedCountedWords.entrySet()) {
            List<String> countWordResultResponse = new ArrayList<>();
            countWordResultResponse.add(entry.getKey());
            countWordResultResponse.add(entry.getValue().toString());
            filteredCountedWords.add(countWordResultResponse);
        }

        Gson gson = new Gson();
        JsonObject countedWordsListObj = new JsonObject();

        JsonElement filteredWords = gson.toJsonTree(filteredCountedWords);
        JsonElement unFilteredWords = gson.toJsonTree(unFilteredCountedWords);

        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("filteredWords", filteredWords);
        countedWordsListObj.add("unFilteredWords", unFilteredWords);

        return countedWordsListObj.toString();
    }

    @Override
    public String createErrorResponse(String errorMessageToUser) {
        Gson gson = new Gson();
        JsonObject errorMessageToUserObj = new JsonObject();

        JsonElement errorMessageToUserJson = gson.toJsonTree(errorMessageToUser);

        errorMessageToUserObj.addProperty("success", false);
        errorMessageToUserObj.add("errorMessageToUser", errorMessageToUserJson);

        return errorMessageToUserObj.toString();
    }
}
