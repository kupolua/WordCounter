package com.qalight.javacourse.controller;


import com.qalight.javacourse.core.WordResultSorter;

public final class CountWordsUserRequest {
    private final String textCount;
    private final WordResultSorter sortingOrder;
    private final boolean isFilterRequired;

    public CountWordsUserRequest(String textCount) {
        this(textCount, null, null);
    }

    public CountWordsUserRequest(String textCount, String sortingOrder, String isFilterRequired) {
        this.textCount = textCount;
        this.sortingOrder = WordResultSorter.getOrderType(sortingOrder);
        this.isFilterRequired = convertToBoolean(isFilterRequired);
    }

    private boolean convertToBoolean(String isFilterWord) {
        return Boolean.valueOf(isFilterWord);
    }

    public String getTextCount() {
        return textCount;
    }

    public WordResultSorter getSortingOrder() {
        return sortingOrder;
    }

    public boolean isFilterRequired() {
        return isFilterRequired;
    }
}
