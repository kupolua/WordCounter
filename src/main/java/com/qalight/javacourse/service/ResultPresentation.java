package com.qalight.javacourse.service;

import java.util.Map;

public interface ResultPresentation {
    public boolean isEligible(String dataTypeResponse);
    public String createResponse(Map<String, Integer> unRefinedCountedWords);
    public String createErrorResponse(String errorMessageToUser);
}