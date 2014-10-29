package com.qalight.javacourse.controller;

import com.qalight.javacourse.core.WordResultSorter;

/**
 * Created by box on 28.10.2014.
 */
public interface CountWordsUserRequest {
    String getTextCount();

    WordResultSorter getSortingOrder();

    boolean isFilterRequired();
}
