package info.deepidea.wordcounter.service;

import java.util.Collection;

public interface RequestSplitter {
    Collection<String> getSplitRequests(String userRequest);
}
