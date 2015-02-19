package info.deepidea.wordcounter.crawler;

import info.deepidea.wordcounter.core.CountWordsProcessor;
import info.deepidea.wordcounter.service.ThreadResultContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Forker extends RecursiveTask<List<ThreadResultContainer>> {
    private static final int SEQUENTIAL_THRESHOLD = 1;
    private final int start;
    private final int end;
    private final boolean internalOnly;
    private final boolean crawlingRequired;
    private final List<String> links;
    private CountWordsProcessor processor;

    public Forker(List<String> links, CountWordsProcessor processor, boolean crawlingRequired, boolean internalOnly) {
        this(links, processor, 0, links.size(), crawlingRequired, internalOnly);
    }

    public Forker(List<String> links, CountWordsProcessor processor, int start,
                  int end, boolean crawlingRequired, boolean internalOnly) {
        this.links = links;
        this.processor = processor;
        this.start = start;
        this.end = end;
        this.crawlingRequired = crawlingRequired;
        this.internalOnly = internalOnly;
    }

    @Override
    protected List<ThreadResultContainer> compute() {
        int length = end - start;
        if (length == SEQUENTIAL_THRESHOLD){
            String url = links.get(start);
            List<ThreadResultContainer> container = Arrays.asList(processor.process(url, crawlingRequired, internalOnly));
            return container;
        }
        Forker one = new Forker(links, processor, start, start + length/2, crawlingRequired, internalOnly);
        one.fork();
        Forker two = new Forker(links, processor, start + length/2, end, crawlingRequired, internalOnly);
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
