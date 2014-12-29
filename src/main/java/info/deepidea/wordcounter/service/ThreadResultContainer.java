package info.deepidea.wordcounter.service;

import java.util.List;
import java.util.Map;

public class ThreadResultContainer {
    private final Map<String, Integer> countedResult;
    private String error;
    private List<String> errorsList;
    private Map<String, Integer> wordStatistic;

    public ThreadResultContainer(Map<String, Integer> countedResult, String error, Map<String, Integer> wordStatistic) {
        this.countedResult = countedResult;
        this.error = error;
        this.wordStatistic = wordStatistic;
    }

    public ThreadResultContainer(Map<String, Integer> countedResult, List<String> errorsList, Map<String, Integer> wordStatistic) {
        this.countedResult = countedResult;
        this.errorsList = errorsList;
        this.wordStatistic = wordStatistic;
    }

    public ThreadResultContainer(Map<String, Integer> countedResult) {
        this.countedResult = countedResult;
    }

    public ThreadResultContainer(Map<String, Integer> countedResult, Map<String, Integer> wordStatistic) {
        this.countedResult = countedResult;
        this.wordStatistic = wordStatistic;
    }

    public Map<String, Integer> getCountedResult() {
        return countedResult;
    }

    public Map<String, Integer> getWordStatistic() {
        return wordStatistic;
    }

    public String getError() {
        return error;
    }

    public List getErrorsList() {
        return errorsList;
    }
}
