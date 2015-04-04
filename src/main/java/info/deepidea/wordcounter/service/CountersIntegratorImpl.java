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
        Map<String, Map<String, Integer>> d3TestData = new HashMap<>();
        List<String> errorsList = new ArrayList<>();
        for (ThreadResultContainer eachContainer : results) {
            boolean resultsHaveSameContainer = checkForSameContainer(d3TestData, eachContainer.getVisitedPage());
            if (!resultsHaveSameContainer) {
                addD3TestData(d3TestData, eachContainer);
                addError(errorsList, eachContainer);
                addResults(resultMap, eachContainer);
                addLinks(relatedLinks, eachContainer);
                addStatistic(resultStatistic, eachContainer);
            }
        }

        return new ThreadResultContainer(resultMap, errorsList, resultStatistic, relatedLinks, d3TestData);
    }

    private boolean checkForSameContainer(Map<String, Map<String, Integer>> wordsByPage, String visitedPage) {
        boolean hasSameResults = false;
        final String slash = "/";                       //WORDS-562

        if (!wordsByPage.isEmpty()) {
            for (String page : wordsByPage.keySet()) {
                String slashedPage = page + slash;
                if (page.equals(visitedPage) || slashedPage.equals(visitedPage)) {
                    hasSameResults = true;
                }
            }
        }
        return hasSameResults;
    }

    private void addD3TestData(Map<String, Map<String, Integer>> d3TestData, ThreadResultContainer eachContainer) {
        if (eachContainer.getVisitedPage() != null) {
            d3TestData.put(eachContainer.getVisitedPage(), eachContainer.getCountedResult());
        }
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
        if (eachContainer.getVisitedPage() != null) {
            Set<String> existingPages = relatedLinks.get(eachContainer.getMarker());
            if (existingPages == null) {
                Set<String> newPages = new HashSet<>(1);
                newPages.add(eachContainer.getVisitedPage());
                relatedLinks.put(eachContainer.getMarker(), newPages);
            } else {
                existingPages.add(eachContainer.getVisitedPage());
                relatedLinks.put(eachContainer.getMarker(), existingPages);
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
