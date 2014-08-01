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

    public List<String> parseUrslList(String stringUrls) {

        final String delim = ",";
        List<String> userCheckUrlsList = new ArrayList<String>(Arrays.asList(stringUrls.replaceAll(" ", "").split(delim)));
        List<String> userUrlsList = new ArrayList<String>();

        for (String url : userCheckUrlsList) {
            if (url.startsWith("http://")) {
                userUrlsList.add(url);
            } else if (url.startsWith("https://")) {
                LOG.warn("Cannot handle <https://> URL.");
                userUrlsList.add("I can't read https");
            } else {
                userUrlsList.add("http://" + url);
            }
        }
        return userUrlsList;
    }

}