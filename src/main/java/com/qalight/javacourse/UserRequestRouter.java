package com.qalight.javacourse;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kpl on 30.07.2014.
 */
// todo kupolua:  if I use HttpServletRequest, that maybe I should make it as servlet?
public class UserRequestRouter {
    private static final Logger LOG = LoggerFactory.getLogger(UserRequestRouter.class);

    public JsonObject getResponse(HttpServletRequest request) {

        String userUrlsString = request.getParameter("userRequest");
        String sortingParam = request.getParameter("userChoice");
        String getTypeStatisticResult = request.getParameter("getTypeStatisticResult");

        try {
            WordsSorter.valueOf(sortingParam);
            WordCounterFactory wordCounterFactory = new WordCounterFactory();
            CountedWordsGetter countedWordsGetter = wordCounterFactory.getTypeRequest(getTypeStatisticResult);

            return countedWordsGetter.getCountedWords(userUrlsString, sortingParam);

        } catch (IllegalArgumentException e) {
            // todo diverfd: Print message to user form
            LOG.error("Invalid sorting parameter: " + sortingParam, e);
            throw new RuntimeException("Invalid sorting parameter: " + sortingParam);
            // todo diverfd: print error html page to user
        }

    }

}