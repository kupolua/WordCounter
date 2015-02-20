package info.deepidea.wordcounter.controller;

import info.deepidea.wordcounter.core.WordResultSorter;

public final class CountWordsUserRequestImpl implements CountWordsUserRequest {
    private final int depthOfCrawling;
    private final boolean internalCrawling;
    private final boolean isFilterRequired;
    private final String textCount;
    private final WordResultSorter sortingOrder;

    public CountWordsUserRequestImpl(String textCount, int depthOfCrawling, boolean internalCrawling) {
        this(textCount, null, null, depthOfCrawling, internalCrawling);
    }

    public CountWordsUserRequestImpl(String textCount, String sortingOrder, String isFilterRequired,
                                     int depthOfCrawling, boolean internalCrawling) {
        this.textCount = textCount;
        this.sortingOrder = WordResultSorter.getOrderType(sortingOrder);
        this.isFilterRequired = convertToBoolean(isFilterRequired);
        this.depthOfCrawling = depthOfCrawling;
        this.internalCrawling = internalCrawling;
    }

    private boolean convertToBoolean(String isFilterWord) {
        return Boolean.valueOf(isFilterWord);
    }

    @Override
    public String getTextCount() {
        return textCount;
    }

    @Override
    public WordResultSorter getSortingOrder() {
        return sortingOrder;
    }

    @Override
    public boolean isFilterRequired() {
        return isFilterRequired;
    }

    @Override
    public int getDepthOfCrawling() {
        return depthOfCrawling;
    }

    @Override
    public boolean isInternalCrawling() {
        return internalCrawling;
    }
}
