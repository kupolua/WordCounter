package info.deepidea.wordcounter.service;

import java.util.List;

public interface CountersIntegrator {
    ThreadResultContainer integrateResults(List<ThreadResultContainer> results);
}
