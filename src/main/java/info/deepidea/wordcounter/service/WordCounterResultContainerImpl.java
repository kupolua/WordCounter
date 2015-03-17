package info.deepidea.wordcounter.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class WordCounterResultContainerImpl implements WordCounterResultContainer {
    private Map<String, Integer> countedResult;
    private List<String> errors;
    private Map<String, Integer> wordStatistic;
    private Map<String, Set<String>> relatedLinks;
    private Map<String, Map<String, Integer>> d3TestData;

    public WordCounterResultContainerImpl() {}

    public WordCounterResultContainerImpl(Map<String, Integer> countedResult, List<String> errors, Map<String, Integer> wordStatistic, Map<String, Set<String>> relatedLinks, Map<String, Map<String, Integer>> d3TestData){
        this.countedResult = countedResult;
        this.errors = errors;
        this.wordStatistic = wordStatistic;
        this.relatedLinks = relatedLinks;
        this.d3TestData = d3TestData;
    }

    @Override
    public Map<String, Integer> getCountedResult() {
        return countedResult;
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public Map<String, Integer> getWordStatistic() {
        return wordStatistic;
    }

    @Override
    public Map<String, Set<String>> getRelatedLinks() {
        return relatedLinks;
    }

    @Override
    public Map<String, Map<String, Integer>> getD3TestData() {
        return d3TestData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordCounterResultContainerImpl that = (WordCounterResultContainerImpl) o;

        if (countedResult != null ? !countedResult.equals(that.countedResult) : that.countedResult != null)
            return false;
        if (errors != null ? !errors.equals(that.errors) : that.errors != null) return false;
        if (wordStatistic != null ? !wordStatistic.equals(that.wordStatistic) : that.wordStatistic != null) return false;
        if (relatedLinks != null ? !relatedLinks.equals(that.relatedLinks) : that.relatedLinks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countedResult != null ? countedResult.hashCode() : 0;
        result = 31 * result + (errors != null ? errors.hashCode() : 0);
        result = 31 * result + (wordStatistic != null ? wordStatistic.hashCode() : 0);
        result = 31 * result + (relatedLinks != null ? relatedLinks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WordCounterResultContainerImpl{" +
                "countedResult=" + countedResult +
                ", errors=" + errors +
                ", wordStatistic=" + wordStatistic +
                ", relatedLinks=" + relatedLinks +
                '}';
    }
}
