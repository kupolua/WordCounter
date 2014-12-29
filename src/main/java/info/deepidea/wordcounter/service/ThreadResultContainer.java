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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThreadResultContainer that = (ThreadResultContainer) o;

        if (countedResult != null ? !countedResult.equals(that.countedResult) : that.countedResult != null) return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (errorsList != null ? !errorsList.equals(that.errorsList) : that.errorsList != null) return false;
        if (wordStatistic != null ? !wordStatistic.equals(that.wordStatistic) : that.wordStatistic != null) return false;

        return true;
    }
}
