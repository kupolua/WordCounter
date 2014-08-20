package com.qalight.javacourse.service;

import java.util.List;
import java.util.Map;

public enum ResultPresentation {

    JSON {
        @Override
        public String createResponse(String textLink, List<Map.Entry<String, Integer>> collectedWordResult, String dataTypeResponse) {
            result = jsonResultPresentation.createResponse(textLink, collectedWordResult, dataTypeResponse);
            return result;
        }

        @Override
        public String createErrorResponse(String errorMessageToUser) {
            result = jsonResultPresentation.createErrorResponse(errorMessageToUser);
            return result;
        }
    };

    public abstract String createResponse(String textLink, List<Map.Entry<String, Integer>> collectedWordResult, String dataTypeResponse);
    public abstract String createErrorResponse(String errorMessageToUser);
    JsonResultPresentation jsonResultPresentation = new JsonResultPresentation();
    public String result;

}
