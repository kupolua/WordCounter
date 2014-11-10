package com.qalight.javacourse.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JsonResultPresentation {
    private Gson gson;

    public JsonResultPresentation(){
        gson = new Gson();
    }

    public String createResponse(Map<String, Integer> unRefinedCountedWords) {
        Assertions.assertObjectIsNotNull(unRefinedCountedWords);

        List<List<String>> unFilteredCountedWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entryUnFilteredWords : unRefinedCountedWords.entrySet()) {
            List<String> countUnFilteredWordsWordResultResponse = new ArrayList<>();
            countUnFilteredWordsWordResultResponse.add(entryUnFilteredWords.getKey());
            countUnFilteredWordsWordResultResponse.add(String.valueOf(entryUnFilteredWords.getValue()));
            unFilteredCountedWords.add(countUnFilteredWordsWordResultResponse);
        }

        JsonElement unFilteredWords = gson.toJsonTree(unFilteredCountedWords);
        JsonObject countedWordsListObj = new JsonObject();
        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("unFilteredWords", unFilteredWords);
        return countedWordsListObj.toString();
    }

    public String createErrorResponse(Throwable e) {
        Assertions.assertObjectIsNotNull(e);
        ErrorDataContainer errorDataContainer = new ErrorDataContainer(e.getMessage());
        String responseErrorMessage = gson.toJson(errorDataContainer);
        return responseErrorMessage;
    }
}