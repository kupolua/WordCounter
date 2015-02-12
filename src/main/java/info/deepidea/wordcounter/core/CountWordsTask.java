package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.crawler.Crawler;
import info.deepidea.wordcounter.crawler.CrawlerImpl;
import info.deepidea.wordcounter.service.ThreadResultContainer;

import java.util.List;
import java.util.concurrent.Callable;

public class CountWordsTask implements Callable<List<ThreadResultContainer>> {
    private final String clientRequest;
    private final CountWordsProcessor wordsProcessor;

    public CountWordsTask(String clientRequest, CountWordsProcessor wordsProcessor) {
        this.clientRequest = clientRequest;
        this.wordsProcessor = wordsProcessor;
    }

    @Override
    public List<ThreadResultContainer> call() throws Exception {
        Crawler crawler = new CrawlerImpl(1, true, clientRequest, wordsProcessor);
        return crawler.crawl();
    }
}
