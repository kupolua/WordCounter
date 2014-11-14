package com.qalight.javacourse.core;

import com.qalight.javacourse.service.ThreadResultContainer;

public interface CountWordsProcessor {
    public ThreadResultContainer process(String clientRequest);
}
