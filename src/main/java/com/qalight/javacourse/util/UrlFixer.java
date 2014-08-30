package com.qalight.javacourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by box on 27.08.2014.
 */
@Component
public class UrlFixer {
    private static final Logger LOG = LoggerFactory.getLogger(UrlFixer.class);
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    public String fixUrl(String userUrl){
        String sourcesWithoutWhitespaces = userUrl.trim();
        if(!(sourcesWithoutWhitespaces.startsWith(HTTPS_PREFIX) || sourcesWithoutWhitespaces.startsWith(HTTP_PREFIX))){
            LOG.debug("Try to fix URL: " + sourcesWithoutWhitespaces);
            sourcesWithoutWhitespaces = HTTP_PREFIX + sourcesWithoutWhitespaces;
        }
        return sourcesWithoutWhitespaces;
    }
}
