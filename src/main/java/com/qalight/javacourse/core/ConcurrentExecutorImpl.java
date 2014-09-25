package com.qalight.javacourse.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    public List<Map<String, Integer>> countAsynchronously(Collection<String> splitterRequests) {
        Collection<CountWordsTask> tasks = createTasks(splitterRequests);
        Collection<Future<Map<String, Integer>>> futures = createFutures(tasks);
        List<Map<String, Integer>> result = waitForAllResults(futures);
        return result;
    }

    private Collection<CountWordsTask> createTasks(Collection<String> requests) {
        List<CountWordsTask> result = new ArrayList<>(requests.size());
        for (String request: requests) {
            CountWordsTask task = new CountWordsTask(request, countWordsProcessor);
            result.add(task);
        }
        return result;
    }

    private Collection<Future<Map<String, Integer>>> createFutures(Collection<CountWordsTask> tasks) {
        List<Future<Map<String, Integer>>> result = new ArrayList<>(tasks.size());
        for (CountWordsTask task: tasks) {
            Future<Map<String, Integer>> future = executorService.submit(task);
            result.add(future);
        }
        return result;
    }

    private List<Map<String, Integer>> waitForAllResults(Collection<Future<Map<String, Integer>>> futures) {
        List<Map<String, Integer>> result = new ArrayList<>(futures.size());
        try {
            for (Future<Map<String, Integer>> future : futures) {
                Map<String, Integer> eachResult = future.get(processingTimeout, TimeUnit.SECONDS);
                result.add(eachResult);
            }
        } catch (InterruptedException e) {
            final String msg = String.format("thread %s was interrupted", Thread.currentThread().getName());
            LOG.error(msg);
            throw new RuntimeException(msg, e);
        } catch (ExecutionException e) {
            final String msg = String.format("Error during executing request: %s", e.getMessage());
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
