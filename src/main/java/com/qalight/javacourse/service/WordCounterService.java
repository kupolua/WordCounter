package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.core.WordResultSorter;

/**
 * Created by kpl on 09.08.2014.
 */
public interface WordCounterService {
    String getWordCounterResult(String dataSources, String sortingParam);
}
