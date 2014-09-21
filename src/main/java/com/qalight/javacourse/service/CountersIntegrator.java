package com.qalight.javacourse.service;

import java.util.List;
import java.util.Map;

public interface CountersIntegrator {
    Map<String, Integer> integrateResults(List<Map<String, Integer>> results);
}
