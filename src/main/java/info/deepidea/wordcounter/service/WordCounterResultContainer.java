package info.deepidea.wordcounter.service;

import java.util.List;
import java.util.Map;

public interface WordCounterResultContainer {
    Map<String, Integer> getCountedResult();
    List<String> getErrors();
    Map<String, Integer> getWordStatistic();
}
