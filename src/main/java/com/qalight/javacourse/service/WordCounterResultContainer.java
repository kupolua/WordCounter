package com.qalight.javacourse.service;

import java.util.List;
import java.util.Map;

public interface WordCounterResultContainer {
    Map<String, Integer> getCountedResult();
    List getErrors();
}
