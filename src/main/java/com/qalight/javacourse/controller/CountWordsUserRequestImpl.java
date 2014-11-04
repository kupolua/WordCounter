package com.qalight.javacourse.controller;

import com.qalight.javacourse.core.WordResultSorter;

public final class CountWordsUserRequestImpl implements CountWordsUserRequest {
    private final String textCount;
    private final WordResultSorter sortingOrder;
    private final boolean isFilterRequired;

    public CountWordsUserRequestImpl(String textCount) {
        this(textCount, null, null);
    }

    public CountWordsUserRequestImpl(String textCount, String sortingOrder, String isFilterRequired) {
        this.textCount = textCount;
        this.sortingOrder = WordResultSorter.getOrderType(sortingOrder);
        this.isFilterRequired = convertToBoolean(isFilterRequired);
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
}
