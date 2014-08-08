package com.qalight.javacourse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * Created by kpl on 08.08.2014.
 */
public class ResponseObjectCreator {
    public JsonObject createResponseObject(List<List<Map.Entry<String, Integer>>> countedWordsList, List<String> userUrlsList) {

        Gson gson = new Gson();
        JsonElement userResponse = gson.toJsonTree(countedWordsList);
        JsonElement listUsersUrls = gson.toJsonTree(userUrlsList);

        JsonObject countedWordsListObj = new JsonObject();
        countedWordsListObj.addProperty("success", true);
        countedWordsListObj.add("response", userResponse);
        countedWordsListObj.add("listUsersUrls", listUsersUrls);

        return countedWordsListObj;
    }
}
