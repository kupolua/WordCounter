package com.qalight.javacourse.service;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SingleDataSourceValidator {
    private static final Logger LOG = LoggerFactory.getLogger(SingleDataSourceValidator.class);

    public String validateSources(String dataSources) {
        String validSource;
        final String HTTP_PREFIX = "http://";
        String[] schemes = {"http"};
        UrlValidator validator = new UrlValidator(schemes);

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

    private String deleteWhitespaces(String dataSources){
        return dataSources.replaceAll(" ", "");
    }
}
