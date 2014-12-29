package info.deepidea.wordcounter.service;

import java.util.List;
import java.util.Map;

public interface WordStatistic {
    Map<String, Integer> getStatistic(String plainText, List<String> refinedWords);
}
