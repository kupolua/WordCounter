package com.qalight.javacourse.service;

import java.util.Map;

public interface ResultPresentation {
    public boolean isEligible(String dataTypeResponse);

    public String createResponse(String textLink, Map<String, Integer> countedWords, Map<String, Integer> unFilteredWords, String dataTypeResponse);

    public String createErrorResponse(String errorMessageToUser);
}
