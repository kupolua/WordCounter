package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.core.WordResultSorter;

/**
 * Created by kpl on 09.08.2014.
 */
public interface WordCounterService {
    DataSourceSplitter sources = new DataSourceSplitter();
    WordCounter wordCounter = new WordCounter();
    WordResult wordResult = new WordResult();
    WordResultSorter wordResultSorter = new WordResultSorter();
    ResultPresentation resultPresentation = new ResultPresentation();

    String getWordCounterResult(String dataSources, String sortingParam);
}
