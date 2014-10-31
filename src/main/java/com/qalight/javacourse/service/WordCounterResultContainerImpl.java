package com.qalight.javacourse.service;

import java.util.Map;

public final class WordCounterResultContainerImpl implements WordCounterResultContainer {
    private final Map<String, Integer> countedResult;

    public WordCounterResultContainerImpl(Map<String, Integer> countedResult){
        this.countedResult = countedResult;
    }

    @Override
    public Map<String, Integer> getCountedResult() {
        return countedResult;
    }
}
