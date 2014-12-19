package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.service.ThreadResultContainer;

import java.util.concurrent.Callable;

public class CountWordsTask implements Callable<ThreadResultContainer> {
    private final String clientRequest;
    private final CountWordsProcessor wordsProcessor;

    public CountWordsTask(String clientRequest, CountWordsProcessor wordsProcessor) {
        this.clientRequest = clientRequest;
        this.wordsProcessor = wordsProcessor;
    }

    @Override
    public ThreadResultContainer call() throws Exception {
        return wordsProcessor.process(clientRequest);
    }
}
