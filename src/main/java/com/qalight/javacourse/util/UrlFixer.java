package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by box on 27.08.2014.
 */
@Component
public class UrlFixer {
    private static final Logger LOG = LoggerFactory.getLogger(UrlFixer.class);
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";
    private static final Pattern URL = Pattern.compile("^(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\.-/%]*)*/?$");

    public String fixRequest(String userRequest){
        String request;
        Matcher matcher = URL.matcher(userRequest);
        if(!matcher.matches()){
            request = userRequest;
        } else {
            request = fixUrl(userRequest);
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
}
