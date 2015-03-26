package info.deepidea.wordcounter.core;

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

    @Autowired
    public CountWordsProcessorImpl(TextTypeInquirer textTypeInquirer, DocumentConverter documentConverter,
                                   WordCounter wordCounter, TextRefiner refiner, WordStatistic statistic) {
        this.textTypeInquirer = textTypeInquirer;
        this.documentConverter = documentConverter;
        this.wordCounter = wordCounter;
        this.refiner = refiner;
        this.statistic = statistic;
    }

    @Override
    public ThreadResultContainer process(String clientRequest, boolean crawlingRequired, boolean internalOnly) {
        LOG.debug(String.format("executing request {%s} in thread '%s'",
                clientRequest, Thread.currentThread().getName()));
        final RequestContainer requestContainer = getRequestContainer(clientRequest, crawlingRequired, internalOnly); //WORDS-563

        ThreadResultContainer result;
        try{
            TextType requestType = textTypeInquirer.inquireTextType(requestContainer.getClientRequest());

            DocumentToStringConverter converter = documentConverter.getDocumentConverter(requestType);

            ConvertedDataContainer convertedData = converter.convertToString(requestContainer);

            List<String> refinedWords = refiner.refineText(convertedData.getPlainText());

            Map<String, Integer> countedResult = wordCounter.countWords(refinedWords);

            Map<String, Integer> wordStatistic = statistic.getStatistic(convertedData.getPlainText(), refinedWords);

            result = new ThreadResultContainer(countedResult, wordStatistic, convertedData.getRelatedLinks());
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
            result = new ThreadResultContainer(e.getMessage());
        }
        return result;
    }

    protected RequestContainer getRequestContainer(String clientRequest, boolean crawlingRequired, boolean internalOnly) {
        return new RequestContainer(clientRequest, crawlingRequired, internalOnly);
    }
}
