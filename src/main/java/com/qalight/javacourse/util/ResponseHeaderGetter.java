package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class ResponseHeaderGetter {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseHeaderGetter.class);

    public String getTextTypeByHttpHeader(String dataSourceLink) {

        checkForNullOrEmpty(dataSourceLink);

        String contentType = "";
        try {
            URL obj = new URL(dataSourceLink);
            URLConnection conn = obj.openConnection();
            Map<String, List<String>> map = conn.getHeaderFields();
            contentType = String.valueOf(map.get("Content-Type"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "It is impossible to determine the type of the document because we can not connect to this link: "
                            + dataSourceLink
            );
        }
        return contentType;
    }

    private void checkForNullOrEmpty(String dataSourceLink) {
        if (dataSourceLink == null) {
            LOG.error("\"dataSourceLink\" received parameter is NULL");
            throw new NullPointerException(
                    "It is impossible to determine the type of the document because the link is null."
            );
        }

        if (dataSourceLink.equals("")) {
            throw new IllegalArgumentException(
                    "It is impossible to determine the type of the document because the link is empty."
            );
        }
    }
}