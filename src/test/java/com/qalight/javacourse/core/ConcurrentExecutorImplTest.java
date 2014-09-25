package com.qalight.javacourse.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConcurrentExecutorImplTest {
    private static final long ONE_SECOND_IN_MILLIS = 1000;
    private static final int DEFAULT_TIMEOUT_SEC = 3;
    private static final int DEFAULT_THREAD_MULTIPLICATION_FACTOR = 1;
    private static final int DEFAULT_MAX_POOL_SIZE = 3;
    private static final ThreadFactory THREAD_FACTORY = Executors.defaultThreadFactory();
    private CountWordsProcessor defaultProcessor;
    private Map<String, Integer> defaultExpectedResult;
    private ConcurrentExecutor executor;

    @Before
    public void setUp() throws Exception {
        defaultExpectedResult = new HashMap<>();
        defaultExpectedResult.put("word", 10);

        defaultProcessor = mock(CountWordsProcessor.class);
        when(defaultProcessor.process(any(String.class))).thenReturn(defaultExpectedResult);

        executor = new ConcurrentExecutorImpl(
                DEFAULT_TIMEOUT_SEC, DEFAULT_THREAD_MULTIPLICATION_FACTOR, DEFAULT_MAX_POOL_SIZE,
                THREAD_FACTORY, defaultProcessor);
    }

    @Test
    public void testCountAsynchronously_singleTasks() throws Exception {
        // given
        Set<String> input = new HashSet<>();
        input.add("word");

        // when
        List<Map<String, Integer>> actual = executor.countAsynchronously(input);

        // then
        verify(defaultProcessor, times(1)).process(any(String.class));

        assertEquals(1, actual.size());
        assertEquals(defaultExpectedResult, actual.get(0));
    }

    @Test
    public void testCountAsynchronously_duplicateTasks() throws Exception {
        // given
        Set<String> input = new HashSet<>();
        input.add("word");
        input.add("word");
        input.add("word");

        // when
        List<Map<String, Integer>> actual = executor.countAsynchronously(input);

        // then
        verify(defaultProcessor, times(1)).process(any(String.class));

        assertEquals(1, actual.size());
        assertEquals(defaultExpectedResult, actual.get(0));
    }

    @Test
    public void testCountAsynchronously_multipleTasks() throws Exception {
        // given
        Set<String> input = new HashSet<>();
        input.add("http://one");
        input.add("http://two");
        input.add("http://three");

        // when
        List<Map<String, Integer>> actual = executor.countAsynchronously(input);

        // then
        verify(defaultProcessor, times(3)).process(any(String.class));

        assertEquals(3, actual.size());
        assertEquals(defaultExpectedResult, actual.get(0));
    }

    @Test(expected = RuntimeException.class)
    public void testCountAsynchronously_throwTimeoutException() throws InterruptedException {
        // given
        Set<String> input = new HashSet<>();
        input.add("word");

        final int poolTimeoutSec = 1;
        final long runningTimeMillis = 2 * ONE_SECOND_IN_MILLIS;

        // when
        CountWordsProcessor longRunningProcessor = mock(CountWordsProcessor.class);
        when(longRunningProcessor.process(any(String.class))).thenAnswer(invocation -> {
            Thread.sleep(runningTimeMillis);
            return defaultExpectedResult;
        });

        executor = new ConcurrentExecutorImpl(
                poolTimeoutSec, DEFAULT_THREAD_MULTIPLICATION_FACTOR, DEFAULT_MAX_POOL_SIZE,
                THREAD_FACTORY, longRunningProcessor);

        executor.countAsynchronously(input);

        // then
        // exception thrown
    }

    @Test(expected = RejectedExecutionException.class)
    public void testCountAsynchronously_throwExceptionWhenPoolIsTooBusy() throws InterruptedException {
        // given
        final int numberOfThreads = 300;

        Set<String> input = new HashSet<>();
        for (int i = 0; i < numberOfThreads; ++i) {
            input.add("word" + i);
        }

        long runningSeconds = 2 * 1000;

        // when
        CountWordsProcessor longRunningProcessor = mock(CountWordsProcessor.class);
        when(defaultProcessor.process(any(String.class))).thenAnswer(invocation -> {
            Thread.sleep(runningSeconds);
            return defaultExpectedResult;
        });

        executor = new ConcurrentExecutorImpl(
                DEFAULT_TIMEOUT_SEC, DEFAULT_THREAD_MULTIPLICATION_FACTOR, DEFAULT_MAX_POOL_SIZE,
                THREAD_FACTORY, longRunningProcessor);

        executor.countAsynchronously(input);

        // then
        // exception thrown
    }

}