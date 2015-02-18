package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CountersIntegratorImpl implements CountersIntegrator {
    @Override
    public ThreadResultContainer integrateResults(List<ThreadResultContainer> results) {
        Assertions.assertObjectIsNotNull(results);
        Map<String, Integer> resultMap = new HashMap<>();
        Map<String, Integer> resultStatistic = new HashMap<>();
        Map<String, Set<String>> relatedLinks = new HashMap<>();
        List<String> errorsList = new ArrayList<>();
        for (ThreadResultContainer eachContainer : results) {
            boolean resultsHaveSameContainer = checkForSameContainer(relatedLinks, eachContainer.getRelatedLinks());
            if (!resultsHaveSameContainer) {
                addError(errorsList, eachContainer);
                addResults(resultMap, eachContainer);
                addLinks(relatedLinks, eachContainer);
                addStatistic(resultStatistic, eachContainer);
            }
        }
        return new ThreadResultContainer(resultMap, errorsList, resultStatistic, relatedLinks);
    }

    private boolean checkForSameContainer(Map<String, Set<String>> storedLinks, Map<String, Set<String>> eachLinks) {
        boolean hasSameResults = false;
        if ((!eachLinks.keySet().isEmpty()) && (storedLinks.keySet().containsAll(eachLinks.keySet()))) {
            hasSameResults = true;
        }
        return hasSameResults;
    }

    private void addError(List<String> errorsList, ThreadResultContainer eachContainer) {
        String error = eachContainer.getError();
        if (error != null){
            errorsList.add(error);
        }
    }

    private void addResults(Map<String, Integer> resultMap, ThreadResultContainer eachContainer) {
        Map<String, Integer> eachResultMap = eachContainer.getCountedResult();
        for (Map.Entry<String, Integer> eachEntry : eachResultMap.entrySet()) {
            Integer count = resultMap.get(eachEntry.getKey());
            if (count == null){
                resultMap.put(eachEntry.getKey(), eachEntry.getValue());
            } else {
                int sum = count + eachEntry.getValue();
                resultMap.put(eachEntry.getKey(), sum);
            }
        }
    }

    private void addLinks(Map<String, Set<String>> relatedLinks, ThreadResultContainer eachContainer) {
        Map<String, Set<String>> links = eachContainer.getRelatedLinks();
        for (Map.Entry<String, Set<String>> each : links.entrySet()) {
            if (!each.getValue().isEmpty()) {
                relatedLinks.putAll(links);
                break;
            }
        }
    }

    private void addStatistic(Map<String, Integer> resultStatistic, ThreadResultContainer eachContainer) {
        Assertions.assertMapIsNotEmpty(eachContainer.getWordStatistic());
        Map<String, Integer> eachResultStatistic = eachContainer.getWordStatistic();
        for (Map.Entry<String, Integer> eachEntry : eachResultStatistic.entrySet()) {
            Integer countStatistic = resultStatistic.get(eachEntry.getKey());
            if (countStatistic == null){
                resultStatistic.put(eachEntry.getKey(), eachEntry.getValue());
            } else {
                int sumStatistic = countStatistic + eachEntry.getValue();
                resultStatistic.put(eachEntry.getKey(), sumStatistic);
            }
        }
    }
}
