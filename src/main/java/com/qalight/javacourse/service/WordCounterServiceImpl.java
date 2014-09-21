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

    @Autowired
    public WordCounterServiceImpl(ResultPresentationService resultPresentationService, RequestSplitter splitter,
                                  ConcurrentExecutor concurrentExecutor, CountersIntegrator integrator) {
        this.concurrentExecutor = concurrentExecutor;
        this.splitter = splitter;
        this.integrator = integrator;
        this.resultPresentationService = resultPresentationService;
    }

    @Override
    public String getWordCounterResult(String clientRequest, String dataTypeResponse) {
        checkParams(clientRequest, dataTypeResponse);

        Collection<String> splitterRequests = splitter.getSplitRequests(clientRequest);

        List<Map<String, Integer>> wordCountResults = concurrentExecutor.countAsynchronously(splitterRequests);

        Map<String, Integer> countedWords = integrator.integrateResults(wordCountResults);

        ResultPresentation resultPresentation = resultPresentationService.getResultPresentation(dataTypeResponse);

        String result = resultPresentation.createResponse(clientRequest, countedWords, dataTypeResponse);

        return result;
    }

    private static void checkParams(String userUrlsString, String dataTypeResponse) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(dataTypeResponse);
    }

}