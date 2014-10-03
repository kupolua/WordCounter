package com.qalight.javacourse.service;

import java.util.Map;

public final class WordCounterResultContainer {
    private final Map<String, Integer> countedResult;

    public WordCounterResultContainer(Map<String, Integer> countedResult){
        this.countedResult = countedResult;
    }

    public Map<String, Integer> getCountedResult() {
        return countedResult;
    }
}
