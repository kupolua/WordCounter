package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.controller.CountWordsUserRequest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface WordCounterService {
    WordCounterResultContainer getWordCounterResult(CountWordsUserRequest dataSources) throws InterruptedException, ExecutionException, TimeoutException;
}
