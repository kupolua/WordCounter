package com.qalight.javacourse.service;

import java.util.Collection;

public interface RequestSplitter {
    Collection<String> getSplitRequests(String userRequest);
}
