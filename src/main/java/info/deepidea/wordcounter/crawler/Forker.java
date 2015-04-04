package info.deepidea.wordcounter.crawler;

import info.deepidea.wordcounter.core.CountWordsProcessor;
import info.deepidea.wordcounter.service.ThreadResultContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Forker extends RecursiveTask<List<ThreadResultContainer>> {
    private static final int SEQUENTIAL_THRESHOLD = 1;
    private final int start;
    private final int end;
    private final boolean internalOnly;
    private final boolean crawlingRequired;
    private final List<String> userRequest;
    private CountWordsProcessor processor;

    public Forker(List<String> userRequest, CountWordsProcessor processor, boolean crawlingRequired, boolean internalOnly) {
        this(userRequest, processor, 0, userRequest.size(), crawlingRequired, internalOnly);
    }

    public Forker(List<String> userRequest, CountWordsProcessor processor, int start,
                  int end, boolean crawlingRequired, boolean internalOnly) {
        this.userRequest = userRequest;
        this.processor = processor;
        this.start = start;
        this.end = end;
        this.crawlingRequired = crawlingRequired;
        this.internalOnly = internalOnly;
    }

    @Override
    protected List<ThreadResultContainer> compute() {
        if (userRequest.isEmpty()) {
            return Collections.emptyList();
        }
        int length = end - start;
        if (length == SEQUENTIAL_THRESHOLD) {
            String url = userRequest.get(start);
            ThreadResultContainer container = processor.process(url, crawlingRequired, internalOnly);
            List<ThreadResultContainer> containersList = Arrays.asList(container);
            return containersList;
        }
        Forker one = new Forker(userRequest, processor, start, start + length / 2, crawlingRequired, internalOnly);
        one.fork();
        Forker two = new Forker(userRequest, processor, start + length / 2, end, crawlingRequired, internalOnly);
        two.fork();

        return joinResults(one, two);
    }

    private List<ThreadResultContainer> joinResults(Forker one, Forker two) {
        List<ThreadResultContainer> finalList = new ArrayList<ThreadResultContainer>();
        finalList.addAll(one.join());
        finalList.addAll(two.join());
        return finalList;
    }
}
