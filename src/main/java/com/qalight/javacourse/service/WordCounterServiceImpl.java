package com.qalight.javacourse.service;

import com.qalight.javacourse.core.ConcurrentExecutor;
import com.qalight.javacourse.util.Assertions;
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

    @Autowired
    public WordCounterServiceImpl(ResultPresentationService resultPresentationService, RequestSplitter splitter,
                                  ConcurrentExecutor concurrentExecutor, CountersIntegrator integrator,
                                  WordFilter wordFilter) {
        this.concurrentExecutor = concurrentExecutor;
        this.splitter = splitter;
        this.integrator = integrator;
        this.resultPresentationService = resultPresentationService;
        this.wordFilter = wordFilter;
    }

    @Override
    public String getWordCounterResult(String clientRequest) {
        checkParams(clientRequest);

        Collection<String> splitterRequests = splitter.getSplitRequests(clientRequest);

        List<Map<String, Integer>> wordCountResults = concurrentExecutor.countAsynchronously(splitterRequests);

        Map<String, Integer> unRefinedCountedWords = integrator.integrateResults(wordCountResults);

        Map<String, Integer> refinedCountedWords = wordFilter.removeUnimportantWords(unRefinedCountedWords);

        //todo: this will be to remove when we will implement WordCounterResult
        ResultPresentation resultPresentation = resultPresentationService.getResultPresentation("json");
        String result = resultPresentation.createResponse(unRefinedCountedWords, refinedCountedWords);

        return result;
    }

    private static void checkParams(String userUrlsString) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
    }

}