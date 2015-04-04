package info.deepidea.wordcounter.service;

import java.util.Map;

public interface WordFilter {
    Map<String, Integer> removeUnimportantWords(Map<String, Integer> unRefinedCountedWords, boolean isFilterRequired);
}
