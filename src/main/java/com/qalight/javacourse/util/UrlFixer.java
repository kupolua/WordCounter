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
    private static final int HTTP_SUCCESS_CODE = 200;
    private static final int HTTP_USER_ERROR_CODE = 400;

    public String fixRequest(String userRequest){
        String request;
        String fixedUrl = addHttpProtocolPrefixIfNotExist(userRequest);
        if(checkUrlAlive(fixedUrl, TIMEOUT)){
            request = fixedUrl;
        } else {
            request = userRequest;
        }
        return request;
    }

    private String addHttpProtocolPrefixIfNotExist(String userUrl) {
        String sourcesWithoutWhitespaces = userUrl.trim();

        boolean isHttpOrHttps = sourcesWithoutWhitespaces.startsWith(HTTPS_PREFIX)
                || sourcesWithoutWhitespaces.startsWith(HTTP_PREFIX);

        if (!isHttpOrHttps) {
            LOG.debug("Try to fix URL: " + sourcesWithoutWhitespaces);
            sourcesWithoutWhitespaces = HTTP_PREFIX + sourcesWithoutWhitespaces;
        }
        return sourcesWithoutWhitespaces;
    }

    public boolean checkUrlAlive(String url, int timeout) {
        url = url.replaceFirst("https", "http");

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            boolean okReturnCode = HTTP_SUCCESS_CODE <= responseCode && responseCode < HTTP_USER_ERROR_CODE;
            return okReturnCode;
        } catch (Throwable e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
