package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.crawler.UrlReceiver;
import info.deepidea.wordcounter.service.*;
import info.deepidea.wordcounter.util.TextRefiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CountWordsProcessorImpl implements CountWordsProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CountWordsProcessorImpl.class);
    private final TextTypeInquirer textTypeInquirer;
    private final DocumentConverter documentConverter;
    private final WordCounter wordCounter;
    private final TextRefiner refiner;
    private final WordStatistic statistic;
    private final UrlReceiver urlReceiver;


    @Autowired
    public CountWordsProcessorImpl(TextTypeInquirer textTypeInquirer, DocumentConverter documentConverter,
                                   WordCounter wordCounter, TextRefiner refiner,
                                   WordStatistic statistic, UrlReceiver urlReceiver) {
        this.textTypeInquirer = textTypeInquirer;
        this.documentConverter = documentConverter;
        this.wordCounter = wordCounter;
        this.refiner = refiner;
        this.statistic = statistic;
        this.urlReceiver = urlReceiver;
    }

    @Override
    public ThreadResultContainer process(String clientRequest, boolean crawlingRequired, boolean internalOnly) {
        LOG.debug(String.format("executing request {%s} in thread '%s'",
                clientRequest, Thread.currentThread().getName()));
        ThreadResultContainer result;
        try{
            TextType textType = textTypeInquirer.inquireTextType(clientRequest);

            DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

            String plainText = documentToStringConverter.convertToString(clientRequest);

            List<String> refinedWords = refiner.refineText(plainText);

            Map<String, Integer> countedResult = wordCounter.countWords(refinedWords);

            Map<String, Integer> wordStatistic = statistic.getStatistic(plainText, refinedWords);

            Map<String, Set<String>> relatedLinks = new HashMap<>();
            if (textType instanceof HtmlTextTypeImpl && crawlingRequired) {
                Set<String> crawledUrls = urlReceiver.getUrlsFromPage(clientRequest, internalOnly);
                relatedLinks.put(clientRequest, crawledUrls);
            }

            result = new ThreadResultContainer(countedResult, wordStatistic, relatedLinks);
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
            result = new ThreadResultContainer(Collections.emptyMap(), e.getMessage(),
                                                Collections.emptyMap(),  Collections.emptyMap());
        }
        return result;
    }
}
