@Component
public class WordFilterImpl implements WordFilter {
    @Autowired
    public WordFilterImpl(

    @Override
    public Map<String, Integer> removeUnimportantWords(Map<String, Integer> unRefinedCountedWords,
                                                       boolean isFilterRequired) {
        if (!isFilterRequired) return unRefinedCountedWords;

package com.qalight.javacourse.service;

import java.util.Map;

public interface WordFilter {
    Map<String, Integer> removeUnimportantWords(Map<String, Integer> unRefinedCountedWords, boolean isFilterRequired);
}
