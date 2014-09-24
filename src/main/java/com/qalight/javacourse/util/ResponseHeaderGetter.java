package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class ResponseHeaderGetter {
    private static final String PLAIN_TEXT_TYPE = "plain_text_type";
    private static final Logger LOG = LoggerFactory.getLogger(ResponseHeaderGetter.class);

    public String getHttpHeader(String userRequestUrl) {
        checkForNullOrEmpty(userRequestUrl);

        String textHttpHeader;

        if (userRequestUrl.trim().startsWith("http")) {
            textHttpHeader = getHeader(userRequestUrl);
        } else {
            textHttpHeader = PLAIN_TEXT_TYPE;
        }
        return textHttpHeader;
    }

    private String getHeader(String userRequestUrl) {
        String httpHeader;
        try {
            URL obj = new URL(userRequestUrl);
            URLConnection conn = obj.openConnection();
            Map<String, List<String>> map = conn.getHeaderFields();
            httpHeader = String.valueOf(map.get("Content-Type"));
        } catch (Exception e) {
            String msg = " Http header is not received from " + userRequestUrl + " url.";
            LOG.error(userRequestUrl + msg, e);
            throw new IllegalArgumentException(userRequestUrl + msg, e);
        }
        return httpHeader;
    }

    private void checkForNullOrEmpty(String userRequestUrl) {
        if (userRequestUrl == null) {
            LOG.error("\"userRequestUrl\"  parameter is NULL");
            throw new IllegalArgumentException(
                    "Text type is not defined because the link is null.");
        }

        if (userRequestUrl.equals("")) {
            LOG.error("\"userRequestUrl\"  parameter is empty.");
            throw new IllegalArgumentException(
                    "Text type is not defined because the link is empty.");
        }
    }
}