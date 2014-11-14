package com.qalight.javacourse.service;

import java.util.List;
import java.util.Map;

public class ThreadResultContainer {
    private final Map<String, Integer> countedResult;
    private String error;
    private List<String> errorsList;

    public ThreadResultContainer(Map<String, Integer> countedResult, String error) {
        this.countedResult = countedResult;
        this.error = error;
    }

    public ThreadResultContainer(Map<String, Integer> countedResult, List<String> errorsList) {
        this.countedResult = countedResult;
        this.errorsList = errorsList;
    }

    public ThreadResultContainer(Map<String, Integer> countedResult) {
        this.countedResult = countedResult;
    }

    public Map<String, Integer> getCountedResult() {
        return countedResult;
    }

    public String getError() {
        return error;
    }

    public List getErrorsList() {
        return errorsList;
    }
}
