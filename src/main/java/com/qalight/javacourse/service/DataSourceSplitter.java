package com.qalight.javacourse.service;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavelkulakovsky on 10.08.14.
 */
public class DataSourceSplitter {
    private List<String> validSources = new ArrayList<>();
    private List<String> invalidSources = new ArrayList<>();

    public void validateSources(String dataSources) {
        final String HTTP_PREFIX = "http://";
        String[] schemes = {"http"};
        UrlValidator validator = new UrlValidator(schemes);

        String sourcesWithoutWhitespaces = deleteWhitespaces(dataSources);
        String[] splitSources = sourcesWithoutWhitespaces.split(",");
        for(String source : splitSources){
            if(validator.isValid(source)){
                validSources.add(source);
            } else {
                if(validator.isValid(HTTP_PREFIX + source)){
                    validSources.add(HTTP_PREFIX + source);
                } else {
                    invalidSources.add(source);
                }
            }
        }
    }

    public List<String> getValidSources() {
        return validSources;
    }

    public List<String> getInvalidSources(){
        return invalidSources;
    }

    private String deleteWhitespaces(String dataSources){
        return dataSources.replaceAll(" ", "");
    }
}