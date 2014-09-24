package com.qalight.javacourse.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ConcurrentExecutor {
    List<Map<String,Integer>> countAsynchronously(Collection<String> splitterRequests);
}
