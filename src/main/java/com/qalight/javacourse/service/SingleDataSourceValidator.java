package com.qalight.javacourse.service;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SingleDataSourceValidator {
    private static final Logger LOG = LoggerFactory.getLogger(SingleDataSourceValidator.class);
    private static final String HTTP_PREFIX = "http://";
    private static final String[] SCHEMES = {"http"};  // todo: add https.
    private static final UrlValidator validator = new UrlValidator(SCHEMES);

    public static String validateSources(String dataSources) {
        String validSource;
        String sourcesWithoutWhitespaces = deleteWhitespaces(dataSources);

        LOG.debug("Starting source validation.");

        if(validator.isValid(sourcesWithoutWhitespaces)){
            validSource = sourcesWithoutWhitespaces;
        } else {
            if(validator.isValid(HTTP_PREFIX + sourcesWithoutWhitespaces)){
                validSource = HTTP_PREFIX + sourcesWithoutWhitespaces;
            } else {
                LOG.error("Source <"+ sourcesWithoutWhitespaces +"> is not valid.");
                throw new IllegalArgumentException("No valid source found.");
            }
        }
        return validSource;
    }

    private static String deleteWhitespaces(String dataSources){
        return dataSources.replaceAll(" ", "");
    }
}
