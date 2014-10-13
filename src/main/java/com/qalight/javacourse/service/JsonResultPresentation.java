package com.qalight.javacourse.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
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
    public String createResponse(Map<String, Integer> unRefinedCountedWords) {
        List<List<String>> unFilteredCountedWords = new ArrayList<>();

        for (Map.Entry<String, Integer> entryUnFilteredWords : unRefinedCountedWords.entrySet()) {
            List<String> countUnFilteredWordsWordResultResponse = new ArrayList<>();
            countUnFilteredWordsWordResultResponse.add(entryUnFilteredWords.getKey());
            countUnFilteredWordsWordResultResponse.add(String.valueOf(entryUnFilteredWords.getValue()));
            unFilteredCountedWords.add(countUnFilteredWordsWordResultResponse);
        }

        Gson gson = new Gson();
        JsonObject countedWordsListObj = new JsonObject();

        JsonElement unFilteredWords = gson.toJsonTree(unFilteredCountedWords);

        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("unFilteredWords", unFilteredWords);

        return String.valueOf(countedWordsListObj);
    }

    @Override
    public String createErrorResponse(String errorMessageToUser) {
        Gson gson = new Gson();
        JsonObject errorMessageToUserObj = new JsonObject();

        JsonElement errorMessageToUserJson = gson.toJsonTree(errorMessageToUser);

        errorMessageToUserObj.addProperty("success", false);
        errorMessageToUserObj.add("errorMessageToUser", errorMessageToUserJson);

        return String.valueOf(errorMessageToUserObj);
    }
}
