package com.qalight.javacourse.service;

import java.util.List;
import java.util.Map;

public final class WordCounterResultContainerImpl implements WordCounterResultContainer {
    private final Map<String, Integer> countedResult;
    private List errors;

    public WordCounterResultContainerImpl(Map<String, Integer> countedResult, List errors){
        this.countedResult = countedResult;
        this.errors = errors;
    }

    @Override
    public Map<String, Integer> getCountedResult() {
        return countedResult;
    }

    @Override
    public List getErrors() {
        return errors;
    }
}
