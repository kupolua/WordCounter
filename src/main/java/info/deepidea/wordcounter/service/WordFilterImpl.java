package info.deepidea.wordcounter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WordFilterImpl implements WordFilter {
    private final String wordsForFilter;

    @Autowired
    public WordFilterImpl(
            @Value("${wordsEN}") String wordsEn,
            @Value("${wordsRU}") String wordsRU,
            @Value("${wordsUA}") String wordsUA) {
        wordsForFilter = getWordsForFilter(wordsEn, wordsRU, wordsUA);
    }

    @Override
    public Map<String, Integer> removeUnimportantWords(Map<String, Integer> unRefinedCountedWords, boolean isFilterRequired) {
        if (!isFilterRequired) return unRefinedCountedWords;

        if (unRefinedCountedWords == null) {
            throw new IllegalArgumentException("results collection should not be null");
        }
        Map<String, Integer> refinedWords = new HashMap<>(unRefinedCountedWords);
        List<String> filter = Arrays.asList(wordsForFilter.split(" "));
        refinedWords.keySet().removeAll(filter);
        return refinedWords;
    }

    private final String getWordsForFilter(String... languages) {
        StringBuilder wordsForFilter = new StringBuilder();
        for (String language : languages){
            wordsForFilter.append(language);
            wordsForFilter.append(" ");
        }
        return wordsForFilter.toString();
    }
}