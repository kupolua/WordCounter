package com.qalight.javacourse.service;

import com.qalight.javacourse.controller.CountWordsUserRequest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface WordCounterService {
    WordCounterResultContainer getWordCounterResult(CountWordsUserRequest dataSources) throws InterruptedException, ExecutionException, TimeoutException;
}
