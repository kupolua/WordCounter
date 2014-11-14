package com.qalight.javacourse.core;

import com.qalight.javacourse.service.ThreadResultContainer;

import java.util.Collection;
import java.util.List;

public interface ConcurrentExecutor {
    List<ThreadResultContainer> countAsynchronously(Collection<String> splitterRequests);
}
