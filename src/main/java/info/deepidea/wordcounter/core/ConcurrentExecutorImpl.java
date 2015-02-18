package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.service.ThreadResultContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@Component
public class ConcurrentExecutorImpl implements ConcurrentExecutor{
    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentExecutorImpl.class);
    private static final int CORES = Runtime.getRuntime().availableProcessors();
    private final int threadsMultiplicationFactor;
    private final int processingTimeout;
    private final ExecutorService executorService;
    private final CountWordsProcessor countWordsProcessor;

    @Autowired
    public ConcurrentExecutorImpl(
            @Value("${executor.timeout.seconds}") int processingTimeout,
            @Value("${executor.multiplication.factor}") int threadsMultiplicationFactor,
            @Value("${executor.max.queue.size}") int maxQueueSize,
            ThreadFactory threadFactory,
            CountWordsProcessor countWordsProcessor) {

        this.threadsMultiplicationFactor = threadsMultiplicationFactor;
        this.processingTimeout = processingTimeout;
        this.countWordsProcessor = countWordsProcessor;

        executorService = new ThreadPoolExecutor(
                CORES, CORES * this.threadsMultiplicationFactor,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(maxQueueSize),
                threadFactory);
    }

    @Override
    public List<ThreadResultContainer> countAsynchronously(Collection<String> splitterRequests,
                                                           int depth, boolean internalOnly) {
        Collection<CountWordsTask> tasks = createTasks(splitterRequests, depth, internalOnly);
        Collection<Future<List<ThreadResultContainer>>> futures = createFutures(tasks);
        List<ThreadResultContainer> result = waitForAllResults(futures);
        return result;
    }

    private Collection<CountWordsTask> createTasks(Collection<String> requests, int depth, boolean internalOnly) {
        List<CountWordsTask> result = new ArrayList<>(requests.size());
        for (String request: requests) {
            CountWordsTask task = new CountWordsTask(request, countWordsProcessor, depth, internalOnly);
            result.add(task);
        }
        return result;
    }

    private Collection<Future<List<ThreadResultContainer>>> createFutures(Collection<CountWordsTask> tasks) {
        List<Future<List<ThreadResultContainer>>> result = new ArrayList<>(tasks.size());
        for (CountWordsTask task: tasks) {
            Future<List<ThreadResultContainer>> future = executorService.submit(task);
            result.add(future);
        }
        return result;
    }

    private List<ThreadResultContainer> waitForAllResults(Collection<Future<List<ThreadResultContainer>>> futures) {
        List<ThreadResultContainer> result = new ArrayList<>(futures.size());
        try {
            for (Future<List<ThreadResultContainer>> future : futures) {
                List<ThreadResultContainer> eachResult = future.get(processingTimeout, TimeUnit.SECONDS);
                result.addAll(eachResult);
            }
        } catch (InterruptedException e) {
            final String msg = String.format("thread %s was interrupted", Thread.currentThread().getName());
            LOG.error(msg);
            throw new RuntimeException(msg, e);
        } catch (ExecutionException e) {
            final String msg = String.format("Error during executing request: %s", e.getCause().getMessage());
            LOG.error(msg);
            throw new RuntimeException(msg, e);
        } catch (TimeoutException e) {
            final String msg = "Request timeout. Server is overloaded or request is too big, please try later.";
            LOG.error(msg);
            throw new RuntimeException(msg, e);
        }
        return result;
    }
}
