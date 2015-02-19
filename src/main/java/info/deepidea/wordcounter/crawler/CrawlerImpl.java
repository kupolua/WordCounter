package info.deepidea.wordcounter.crawler;

import info.deepidea.wordcounter.core.CountWordsProcessor;
import info.deepidea.wordcounter.service.ThreadResultContainer;
import info.deepidea.wordcounter.util.Assertions;
import info.deepidea.wordcounter.util.WordCounterArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

import static info.deepidea.wordcounter.util.ErrorCodeImpl.*;

public class CrawlerImpl implements Crawler {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerImpl.class);
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
        final Set<String> initialUrl = new HashSet<>(Arrays.asList(userRequest));

        Assertions.assertStringIsNotNullOrEmpty(userRequest);
        checkDepth();

        List<Set<String>> depthsOfUrls = new ArrayList<Set<String>>();
        depthsOfUrls.add(initialUrl);

        ForkJoinPool pool = getForkJoinPool();

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
            newUrls.removeAll(mergeResults(depthsOfUrls)); //todo: potential issue (same links result)
            depthsOfUrls.add(newUrls);
            resultContainers.addAll(newContainers);
        }

        return resultContainers;
    }

    private void checkDepth() {
        final int maxDepth = 2;
        final int minDepth = 0;
        if (depth > maxDepth || depth < minDepth){
            final String msg = String.format("Depth could not be > %d or < %d", maxDepth, minDepth);
            LOG.error(msg);
            throw new WordCounterArgumentException(DEPTH_IS_OUT_OF_RANGE);
        }
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
