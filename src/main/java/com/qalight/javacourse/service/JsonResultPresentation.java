package com.qalight.javacourse.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonResultPresentation implements ResultPresentation {
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
    public String createResponse(String textLink, Map<String, Integer> countedWords, Map<String, Integer> unFilteredWords, String dataTypeResponse) {
        List<List<String>> countWordsResultResponse = new ArrayList<>();
        List<List<String>> countUnFilteredWordsResultResponse = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : countedWords.entrySet()) {
            List<String> countWordResultResponse = new ArrayList<>();
            countWordResultResponse.add(entry.getKey());
            countWordResultResponse.add(entry.getValue().toString());
            countWordsResultResponse.add(countWordResultResponse);
        }
        for (Map.Entry<String, Integer> entryUnFilteredWords : unFilteredWords.entrySet()) {
            List<String> countUnFilteredWordsWordResultResponse = new ArrayList<>();
            countUnFilteredWordsWordResultResponse.add(entryUnFilteredWords.getKey());
            countUnFilteredWordsWordResultResponse.add(entryUnFilteredWords.getValue().toString());
            countUnFilteredWordsResultResponse.add(countUnFilteredWordsWordResultResponse);
        }

        Gson gson = new Gson();
        JsonObject countedWordsListObj = new JsonObject();

        JsonElement filteredWords = gson.toJsonTree(countWordsResultResponse);
        JsonElement listUnFilteredWords = gson.toJsonTree(countUnFilteredWordsResultResponse);

        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("filteredWords", filteredWords);
        countedWordsListObj.add("dataAjax", listUnFilteredWords);

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
