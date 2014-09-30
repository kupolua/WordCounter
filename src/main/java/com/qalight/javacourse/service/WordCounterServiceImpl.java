package com.qalight.javacourse.service;

import com.qalight.javacourse.core.ConcurrentExecutor;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.PropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service("wordCounterService")
public class WordCounterServiceImpl implements WordCounterService {
    private final RequestSplitter splitter;
    private final ConcurrentExecutor concurrentExecutor;
    private final CountersIntegrator integrator;
    private final ResultPresentationService resultPresentationService;
    private final WordFilter wordFilter;
    private final PropertiesReader propertiesReader;

    @Autowired
    public WordCounterServiceImpl(ResultPresentationService resultPresentationService, RequestSplitter splitter,
                                  ConcurrentExecutor concurrentExecutor, CountersIntegrator integrator,
                                  WordFilter wordFilter, PropertiesReader propertiesReader) {
        this.concurrentExecutor = concurrentExecutor;
        this.splitter = splitter;
        this.integrator = integrator;
        this.resultPresentationService = resultPresentationService;
        this.wordFilter = wordFilter;
        this.propertiesReader = propertiesReader;
    }

    @Override
    public String getWordCounterResult(String clientRequest, String dataTypeResponse) {
        checkParams(clientRequest, dataTypeResponse);

        Collection<String> splitterRequests = splitter.getSplitRequests(clientRequest);

        List<Map<String, Integer>> wordCountResults = concurrentExecutor.countAsynchronously(splitterRequests);

        Map<String, Integer> unRefinedCountedWords = integrator.integrateResults(wordCountResults);

        Map<String, Integer> refinedCountedWords = wordFilter.removeUnimportantWords(unRefinedCountedWords);

        ResultPresentation resultPresentation = resultPresentationService.getResultPresentation(dataTypeResponse);

        Map<String, String> webFormProperties = propertiesReader.readProperties();

        String result = resultPresentation.createResponse(unRefinedCountedWords, refinedCountedWords, webFormProperties);

        return result;
    }

    private static void checkParams(String userUrlsString, String dataTypeResponse) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(dataTypeResponse);
    }

}