package com.qalight.javacourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stkotok on 19.07.2014.
 */
public class StringUrlsParser {
    private static final Logger LOG = LoggerFactory.getLogger(StringUrlsParser.class);
    private static final String DELIMETER = ",";

    public List<String> parseUrlList(String stringUrls) {
        String urls = stringUrls.replaceAll(" ", "");
        String[] urlArr = urls.split(DELIMETER);
        List<String> userCheckUrlsList = Arrays.asList(urlArr);
        List<String> userUrlsList = new ArrayList<String>();

        for (String url : userCheckUrlsList) {
            if (url.startsWith("http://")) {
                userUrlsList.add(url);
            } else if (url.startsWith("https://")) {
                LOG.warn("I can not handle <https://> URL.");
                // todo: don't add error as item in list. use exception and handle it
                userUrlsList.add("I can't read https");
            } else {
                userUrlsList.add("http://" + url);
            }
        }
        return userUrlsList;
    }

}