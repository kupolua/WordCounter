package com.qalight.javacourse.core;

import java.util.Map;
import java.util.concurrent.Callable;

public class CountWordsTask implements Callable<Map<String, Integer>> {
    private final String clientRequest;
    private final CountWordsProcessor wordsProcessor;

    public CountWordsTask(String clientRequest, CountWordsProcessor wordsProcessor) {
        this.clientRequest = clientRequest;
        this.wordsProcessor = wordsProcessor;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
        return wordsProcessor.process(clientRequest);
    }
}
