package com.qalight.javacourse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stkotok on 19.07.2014.
 */
public class StringUrlsParser {
    private static final Logger LOG = LoggerFactory.getLogger(StringUrlsParser.class);
    private static final String DELIMETER = ",";

    public List<String> parseUrlList(String stringUrls) {
        String urlsWithoutWhitespaces = stringUrls.replaceAll(" ", "");
        String[] urlsThatDelimitedByComma = urlsWithoutWhitespaces.split(DELIMETER);
        List<String> checkedUrls = new ArrayList<String>();

        for (String url : urlsThatDelimitedByComma) {
            if (url.startsWith("http://")) {
                checkedUrls.add(url);
            } else if (url.startsWith("https://")) {
                LOG.warn("Cannot handle <"+ url +">. https protocol does not allowed.");
                // todo: don't add error as item in list. use exception and handle it
                checkedUrls.add("I can't read https");
            } else {
                checkedUrls.add("http://" + url);
            }
        }
        return checkedUrls;
    }

}