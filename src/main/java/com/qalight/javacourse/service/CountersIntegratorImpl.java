package com.qalight.javacourse.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CountersIntegratorImpl implements CountersIntegrator {
    @Override
    public Map<String, Integer> integrateResults(List<Map<String, Integer>> results) {
        if (results == null) {
            throw new IllegalArgumentException("results collection should not be null");
        }

        Map<String, Integer> result = new HashMap<>(results.size());
        for (Map<String, Integer> eachResultMap : results){
            for (Map.Entry<String, Integer> eachEntry : eachResultMap.entrySet()) {
                Integer count = result.get(eachEntry.getKey());
                if (count == null){
                    result.put(eachEntry.getKey(), eachEntry.getValue());
                } else {
                    int sum = count + eachEntry.getValue();
                    result.put(eachEntry.getKey(), sum);
                }
            }
        }
        return result;
    }
}
