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
    private final UserTextRequest textRequest;

    @Autowired
    public WordCounterServiceImpl(RequestSplitter splitter, ConcurrentExecutor concurrentExecutor, CountersIntegrator integrator, UserTextRequest textRequest) {
        this.concurrentExecutor = concurrentExecutor;
        this.splitter = splitter;
        this.integrator = integrator;
        this.textRequest = textRequest;
    }

    @Override
    public WordCounterResultContainer getWordCounterResult(String clientRequest, String sortingField, String sortingOrder, String isFilterWords) {
        checkParams(clientRequest);

        Collection<String> splitterRequests = splitter.getSplitRequests(clientRequest);

        List<Map<String, Integer>> wordCountResults = concurrentExecutor.countAsynchronously(splitterRequests);

        Map<String, Integer> refinedCountedWords = integrator.integrateResults(wordCountResults);

        Map<String, Integer> sortedCountedWords = textRequest.getSortedMap(refinedCountedWords, sortingField, sortingOrder, isFilterWords);

        WordCounterResultContainer result = new WordCounterResultContainer(sortedCountedWords);

        return result;
    }

    private static void checkParams(String userUrlsString) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
    }

}