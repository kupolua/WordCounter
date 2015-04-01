package info.deepidea.wordcounter.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThreadResultContainer {
    private final Map<String, Integer> countedResult;
    private String error;
    private List<String> errorsList;
    private Map<String, Set<String>> relatedLinks;
    private Map<String, Integer> wordStatistic;
    private Map<String, Map<String, Integer>> d3TestData;
    private String visitedPage;

    public ThreadResultContainer(String error) {
        this.error = error;
        countedResult = Collections.emptyMap();
        wordStatistic = Collections.emptyMap();
        relatedLinks = Collections.emptyMap();
    }

    public ThreadResultContainer(Map<String, Integer> countedResult, List<String> errorsList, Map<String, Integer> wordStatistic, Map<String, Set<String>> relatedLinks, Map<String, Map<String, Integer>> d3TestData) {
        this.countedResult = countedResult;
        this.errorsList = errorsList;
        this.wordStatistic = wordStatistic;
        this.relatedLinks = relatedLinks;
        this.d3TestData = d3TestData;

    }

    public ThreadResultContainer(Map<String, Integer> countedResult, Map<String, Integer> wordStatistic, Map<String, Set<String>> relatedLinks, String visitedPage) {
        this.countedResult = countedResult;
        this.wordStatistic = wordStatistic;
        this.relatedLinks = relatedLinks;
        this.visitedPage = visitedPage;
    }

    public Map<String, Integer> getCountedResult() {
        return countedResult;
    }

    public Map<String, Set<String>> getRelatedLinks() {
        return relatedLinks;
    }

    public Map<String, Integer> getWordStatistic() {
        return wordStatistic;
    }

    public String getError() {
        return error;
    }

    public List<String> getErrorsList() {
        return errorsList;
    }

    public Map<String, Map<String, Integer>> getD3TestData() {
        return d3TestData;
    }

    public String getVisitedPage() {
        return visitedPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThreadResultContainer that = (ThreadResultContainer) o;

        if (countedResult != null ? !countedResult.equals(that.countedResult) : that.countedResult != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (errorsList != null ? !errorsList.equals(that.errorsList) : that.errorsList != null) return false;
        if (relatedLinks != null ? !relatedLinks.equals(that.relatedLinks) : that.relatedLinks != null) return false;
        if (wordStatistic != null ? !wordStatistic.equals(that.wordStatistic) : that.wordStatistic != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countedResult != null ? countedResult.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (errorsList != null ? errorsList.hashCode() : 0);
        result = 31 * result + (relatedLinks != null ? relatedLinks.hashCode() : 0);
        result = 31 * result + (wordStatistic != null ? wordStatistic.hashCode() : 0);
        return result;
    }
}
