package info.deepidea.wordcounter.controller;

import info.deepidea.wordcounter.core.WordResultSorter;

/**
 * Created by box on 28.10.2014.
 */
public interface CountWordsUserRequest {
    String getTextCount();

    WordResultSorter getSortingOrder();

    boolean isFilterRequired();
}
