package com.qalight.javacourse.service;

import com.qalight.javacourse.controller.CountWordsUserRequest;
import com.qalight.javacourse.core.ConcurrentExecutor;
import com.qalight.javacourse.core.WordResultSorter;
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
    private final WordFilter filter;

    @Autowired
    public WordCounterServiceImpl(RequestSplitter splitter, ConcurrentExecutor concurrentExecutor,
                                  CountersIntegrator integrator, WordFilter filter) {
        this.concurrentExecutor = concurrentExecutor;
        this.splitter = splitter;
        this.integrator = integrator;
        this.filter = filter;
    }

    @Override
    public WordCounterResultContainer getWordCounterResult(CountWordsUserRequest clientRequest) {
        checkParams(clientRequest.getTextCount()); // todo: check other type or remove and check all in CountWordsUserRequest

        Collection<String> splitterRequests = splitter.getSplitRequests(clientRequest.getTextCount());

        List<Map<String, Integer>> wordCountResults = concurrentExecutor.countAsynchronously(splitterRequests);

        Map<String, Integer> results = integrator.integrateResults(wordCountResults);

        Map<String, Integer> filteredResults = filter.removeUnimportantWords(results, clientRequest.isFilterRequired());

        WordResultSorter sorter = clientRequest.getSortingOrder();
        Map<String, Integer> sortedRefinedCountedWords = sorter.getSortedWords(filteredResults);

        WordCounterResultContainer result = new WordCounterResultContainer(sortedRefinedCountedWords);

        return result;
    }

    private static void checkParams(String userUrlsString) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
    }

}