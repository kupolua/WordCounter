package com.qalight.javacourse.service;

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

    @Autowired
    public WordCounterServiceImpl(RequestSplitter splitter, ConcurrentExecutor concurrentExecutor, CountersIntegrator integrator) {
        this.concurrentExecutor = concurrentExecutor;
        this.splitter = splitter;
        this.integrator = integrator;
    }

    @Override
    public WordCounterResultContainer getWordCounterResult(String clientRequest) {
        checkParams(clientRequest);

        Collection<String> splitterRequests = splitter.getSplitRequests(clientRequest);

        List<Map<String, Integer>> wordCountResults = concurrentExecutor.countAsynchronously(splitterRequests);
        System.out.println(wordCountResults);

        Map<String, Integer> refinedCountedWords = integrator.integrateResults(wordCountResults);

        Map<String, Integer> sortedRefinedCountedWords = WordResultSorter.VALUE_DESCENDING.getSortedWords(refinedCountedWords);

        WordCounterResultContainer result = new WordCounterResultContainer(sortedRefinedCountedWords);

        return result;
    }

    private static void checkParams(String userUrlsString) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
    }

}