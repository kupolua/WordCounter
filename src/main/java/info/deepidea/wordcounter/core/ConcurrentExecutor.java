package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.service.ThreadResultContainer;

import java.util.Collection;
import java.util.List;

public interface ConcurrentExecutor {
    List<ThreadResultContainer> countAsynchronously(Collection<String> splitterRequests,
                                                    int depth, boolean internalOnly);
}
