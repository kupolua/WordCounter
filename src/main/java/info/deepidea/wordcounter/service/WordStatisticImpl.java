package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import org.springframework.stereotype.Component;

import java.util.*;

import static info.deepidea.wordcounter.util.ViewsConstants.*;

@Component
public class WordStatisticImpl implements WordStatistic {

    @Override
    public Map<String, Integer> getStatistic(String plainText, List<String> refinedWords) {
        Assertions.assertStringIsNotNullOrEmpty(plainText);
        Assertions.assertListIsNotEmpty(refinedWords);

        Map<String, Integer> wordStatistic = new HashMap<>();
        Set<String> uniqueWordsList = new HashSet<String>(refinedWords);
        int totalWords  = refinedWords.size();
        int totalCharacters = plainText.length();
        int charactersWithoutSpaces = plainText.replaceAll("([ \n])", "").length();
        int uniqueWords = uniqueWordsList.size();

        wordStatistic.put(TOTAL_WORDS, totalWords);
        wordStatistic.put(TOTAL_CHARACTERS, totalCharacters);
        wordStatistic.put(CHARACTERS_WITHOUT_SPACES, charactersWithoutSpaces);
        wordStatistic.put(UNIQUE_WORDS, uniqueWords);
        return wordStatistic;
    }
}
