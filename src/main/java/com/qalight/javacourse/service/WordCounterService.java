package com.qalight.javacourse.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface WordCounterService {
    String getWordCounterResult(String dataSources, String dataTypeResponse) throws InterruptedException, ExecutionException, TimeoutException;
}
