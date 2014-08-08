package com.qalight.javacourse;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kpl on 30.07.2014.
 */
// todo kupolua:  if I use HttpServletRequest maybe I should make it as servlet?
public class UserRequestRouter {

    public JsonObject getResponse(HttpServletRequest request) {

        String userUrlsString = request.getParameter("userRequest");
        String sortingParam = request.getParameter("userChoice");
        String typeStatisticResult = request.getParameter("typeStatisticResult");

        WordCounterFactory wordCounterFactory = new WordCounterFactory();
        CountedWordsGetter countedWordsGetter = wordCounterFactory.getTypeRequest(typeStatisticResult);

        return countedWordsGetter.getCountedWords(userUrlsString, sortingParam);
    }

}