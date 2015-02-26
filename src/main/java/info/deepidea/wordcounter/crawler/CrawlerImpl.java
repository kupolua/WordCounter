package info.deepidea.wordcounter.crawler;

import info.deepidea.wordcounter.core.CountWordsProcessor;
import info.deepidea.wordcounter.service.ThreadResultContainer;
import info.deepidea.wordcounter.util.Assertions;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class CrawlerImpl implements Crawler {
    private final int depth;
    private final boolean internalOnly;
    private final String userRequest;
    private final CountWordsProcessor processor;

    public CrawlerImpl(int depth, boolean internalOnly, String userRequest, CountWordsProcessor processor) {
        this.depth = depth;
        this.internalOnly = internalOnly;
        this.userRequest = userRequest;
        this.processor = processor;
    }

    @Override
    public List<ThreadResultContainer> crawl() {
        boolean crawlingRequired = true;
        final List<ThreadResultContainer> resultContainers = new ArrayList<>();
        final Set<String> initialUrl = new HashSet<String>(Arrays.asList(userRequest));

        Assertions.assertStringIsNotNullOrEmpty(userRequest);

        List<Set<String>> depthsOfUrls = new ArrayList<Set<String>>();
        depthsOfUrls.add(initialUrl);

        ForkJoinPool pool = getForkJoinPool();
        System.out.println(depth);

        for (int currentDepth = 0; currentDepth <= depth; currentDepth++) {
            if (currentDepth == depth) {
                crawlingRequired = false;
            }
            Forker forker = getForker(Collections.unmodifiableSet(depthsOfUrls.get(currentDepth)), crawlingRequired);
            List<ThreadResultContainer> newContainers = pool.invoke(forker);
            Set<String> newUrls = new HashSet<String>(extractUrls(newContainers));
            if (newUrls.isEmpty()) {
                resultContainers.addAll(newContainers);
                return resultContainers;
            }
            newUrls.removeAll(mergeResults(depthsOfUrls));
            depthsOfUrls.add(newUrls);
            resultContainers.addAll(newContainers);
        }

        return resultContainers;
    }

    protected ForkJoinPool getForkJoinPool() {
        return new ForkJoinPool();
    }

    protected Forker getForker(Set<String> urls, boolean crawlingRequired) {
        final List<String> links = new ArrayList<>(urls);
        return new Forker(links, processor, crawlingRequired, internalOnly);
    }

    private Set<String> extractUrls(List<ThreadResultContainer> newContainers) {
        Set<String> extractedUrls = new HashSet<String>();
        for (ThreadResultContainer eachContainer : newContainers) {
            for (Set<String> each : eachContainer.getRelatedLinks().values()) {
                extractedUrls.addAll(each);
            }
        }
        return extractedUrls;
    }

    private Set<String> mergeResults(List<Set<String>> depthsOfUrls) {
        Set<String> mergedResults = new HashSet<String>();
        for (Set<String> depth : depthsOfUrls){
            mergedResults.addAll(depth);
        }
        return mergedResults;
    }
}
