package com.qalight.javacourse.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface WordCounterService {
    WordCounterResultContainer getWordCounterResult(String dataSources, String sortingField, String sortingOrder, String isFilterWords) throws InterruptedException, ExecutionException, TimeoutException;
}
