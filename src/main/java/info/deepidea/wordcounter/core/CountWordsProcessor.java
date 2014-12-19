package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.service.ThreadResultContainer;

public interface CountWordsProcessor {
    public ThreadResultContainer process(String clientRequest);
}
