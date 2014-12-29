package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CountersIntegratorImpl implements CountersIntegrator {
    @Override
    public ThreadResultContainer integrateResults(List<ThreadResultContainer> results) {
        Assertions.assertObjectIsNotNull(results);
        //todo: why we should to know results.size()?
//        Map<String, Integer> resultMap = new HashMap<>(results.size());
        Map<String, Integer> resultMap = new HashMap<>();
        Map<String, Integer> resultStatistic = new HashMap<>();
        List<String> errorsList = new ArrayList<>();
        for (ThreadResultContainer eachContainer : results){
            addError(errorsList, eachContainer);
            addResults(resultMap, eachContainer);
            addStatistic(resultStatistic, eachContainer);
        }
        return new ThreadResultContainer(resultMap, errorsList, resultStatistic);
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

    private void addError(List<String> errorsList, ThreadResultContainer eachContainer) {
        String error = eachContainer.getError();
        if (error != null){
            errorsList.add(error);
        }
    }
}
