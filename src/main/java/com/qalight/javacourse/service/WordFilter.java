package com.qalight.javacourse.service;

import java.util.Map;

/**
 * Created by box on 12.10.2014.
 */
public interface WordFilter {
    Map<String, Integer> removeUnimportantWords(Map<String, Integer> unRefinedCountedWords, boolean isFilterRequired);
}
