package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.controller.CountWordsUserRequest;
import info.deepidea.wordcounter.core.ConcurrentExecutor;
import info.deepidea.wordcounter.core.WordResultSorter;
import info.deepidea.wordcounter.util.Assertions;
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
        checkParams(clientRequest.getTextCount(), clientRequest.getSortingOrder());

        Collection<String> splitterRequests = splitter.getSplitRequests(clientRequest.getTextCount());

        List<ThreadResultContainer> wordCountResults = concurrentExecutor.countAsynchronously(splitterRequests);

        ThreadResultContainer results = integrator.integrateResults(wordCountResults);

        Map<String, Integer> filteredResults = filter.removeUnimportantWords(results.getCountedResult(), clientRequest.isFilterRequired());

        WordResultSorter sorter = clientRequest.getSortingOrder();
        Map<String, Integer> sortedRefinedCountedWords = sorter.getSortedWords(filteredResults);

        WordCounterResultContainer result = new WordCounterResultContainerImpl(sortedRefinedCountedWords, results.getErrorsList(), results.getWordStatistic());

        return result;
    }

    private static void checkParams(String userUrlsString, Object obj) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertObjectIsNotNull(obj);
    }
}