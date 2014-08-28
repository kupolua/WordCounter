package com.qalight.javacourse.service;

import java.util.List;
import java.util.Map;

public interface ResultPresentation {
    public Boolean isEligible(String dataTypeResponse);
    public String createResponse(String textLink, List<Map.Entry<String, Integer>> collectedWordResult, String dataTypeResponse);
    public String createErrorResponse(String errorMessageToUser);
}
