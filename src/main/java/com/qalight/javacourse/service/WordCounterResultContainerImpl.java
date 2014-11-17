package com.qalight.javacourse.service;

import java.util.List;
import java.util.Map;

public final class WordCounterResultContainerImpl implements WordCounterResultContainer {
    private Map<String, Integer> countedResult;
    private List errors;

    public WordCounterResultContainerImpl() {};

    public WordCounterResultContainerImpl(Map<String, Integer> countedResult, List errors){
        this.countedResult = countedResult;
        this.errors = errors;
    }

    @Override
    public Map<String, Integer> getCountedResult() {
        return countedResult;
    }

    @Override
    public List getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordCounterResultContainerImpl that = (WordCounterResultContainerImpl) o;

        if (countedResult != null ? !countedResult.equals(that.countedResult) : that.countedResult != null)
            return false;
        if (errors != null ? !errors.equals(that.errors) : that.errors != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countedResult != null ? countedResult.hashCode() : 0;
        result = 31 * result + (errors != null ? errors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WordCounterResultContainerImpl{" +
                "countedResult=" + countedResult +
                ", errors=" + errors +
                '}';
    }
}
