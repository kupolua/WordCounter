package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by box on 27.08.2014.
 */
@Component
public class UrlFixer {
    private static final Logger LOG = LoggerFactory.getLogger(UrlFixer.class);
    private static final int TIMEOUT = 1000;
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    public String fixRequest(String userRequest){
        String request;
        String fixedUrl = fixUrl(userRequest);
        if(checkUrl(fixedUrl, TIMEOUT)){
            request = fixedUrl;
        } else {
            request = userRequest;
        }
        return request;
    }

    private String fixUrl(String userUrl) {
        String sourcesWithoutWhitespaces = userUrl.trim();
        if (!(sourcesWithoutWhitespaces.startsWith(HTTPS_PREFIX) || sourcesWithoutWhitespaces.startsWith(HTTP_PREFIX))) {
            LOG.debug("Try to fix URL: " + sourcesWithoutWhitespaces);
            sourcesWithoutWhitespaces = HTTP_PREFIX + sourcesWithoutWhitespaces;
        }
        return sourcesWithoutWhitespaces;
    }

    private boolean checkUrl(String url, int timeout) {
        url = url.replaceFirst("https", "http");

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (Exception e) {
            return false;
        }
    }
}
