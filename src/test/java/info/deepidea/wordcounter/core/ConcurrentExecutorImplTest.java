package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.service.ThreadResultContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;

import static org.junit.Assert.assertEquals;
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
    private ThreadResultContainer defaultExpectedResult;
    private ConcurrentExecutor executor;

    @Before
    public void setUp() throws Exception {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("word", 10);

        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 21);
            put("statisticUniqueWords", 3);
            put("statisticTotalCharacters", 23);
            put("statisticTotalWords", 3);
        }};

        defaultExpectedResult = new ThreadResultContainer(expectedMap,expectedStatistic, Collections.emptyMap());

        defaultProcessor = mock(CountWordsProcessor.class);
        when(defaultProcessor.process(any(String.class), anyBoolean(), anyBoolean())).thenReturn(defaultExpectedResult);

        executor = new ConcurrentExecutorImpl(
                DEFAULT_TIMEOUT_SEC, DEFAULT_THREAD_MULTIPLICATION_FACTOR, DEFAULT_MAX_POOL_SIZE,
                THREAD_FACTORY, defaultProcessor);
    }

    @Test
    public void testCountAsynchronously_singleTasks() throws Exception {
        // given
        final int depth = 0;
        final boolean internalOnly = true;
        Collection<String> input = new ArrayList<>();
        input.add("word");
        List<ThreadResultContainer> expected = new ArrayList<>();
        expected.add(defaultExpectedResult);

        // when
        List<ThreadResultContainer> actual = executor.countAsynchronously(input, depth, internalOnly);

        // then
        verify(defaultProcessor, times(1)).process(any(String.class), anyBoolean(), anyBoolean());

        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void testCountAsynchronously_multipleTasks() throws Exception {
        // given
        final int depth = 0;
        final boolean internalOnly = true;
        Collection<String> input = new ArrayList<>();
        input.add("http://one");
        input.add("http://two");
        input.add("http://three");
        List<ThreadResultContainer> expected = new ArrayList<ThreadResultContainer>();
        expected.add(defaultExpectedResult);
        expected.add(defaultExpectedResult);
        expected.add(defaultExpectedResult);

        // when
        List<ThreadResultContainer> actual = executor.countAsynchronously(input, depth, internalOnly);

        // then
        verify(defaultProcessor, times(3)).process(any(String.class), anyBoolean(), anyBoolean());

        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testCountAsynchronously_throwTimeoutException() throws InterruptedException {
        // given
        final int depth = 0;
        final boolean internalOnly = true;
        Collection<String> input = new ArrayList<>();
        input.add("word");

        final int poolTimeoutSec = 1;
        final long runningTimeMillis = 2 * ONE_SECOND_IN_MILLIS;

        // when
        CountWordsProcessor longRunningProcessor = mock(CountWordsProcessor.class);
        when(longRunningProcessor.process(any(String.class), anyBoolean(), anyBoolean())).thenAnswer(invocation -> {
            Thread.sleep(runningTimeMillis);
            return defaultExpectedResult.getCountedResult();
        });

        executor = new ConcurrentExecutorImpl(
                poolTimeoutSec, DEFAULT_THREAD_MULTIPLICATION_FACTOR, DEFAULT_MAX_POOL_SIZE,
                THREAD_FACTORY, longRunningProcessor);

        executor.countAsynchronously(input, depth, internalOnly);

        // then
        // exception thrown
    }

    @Test(expected = RejectedExecutionException.class)
    public void testCountAsynchronously_throwExceptionWhenPoolIsTooBusy() throws InterruptedException {
        // given
        final int depth = 0;
        final boolean internalOnly = true;
        final int numberOfThreads = 300;

        Collection<String> input = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; ++i) {
            input.add("word" + i);
        }

        long runningSeconds = 2 * 1000;

        // when
        CountWordsProcessor longRunningProcessor = mock(CountWordsProcessor.class);
        when(defaultProcessor.process(any(String.class), anyBoolean(), anyBoolean())).thenAnswer(invocation -> {
            Thread.sleep(runningSeconds);
            return defaultExpectedResult.getCountedResult();
        });

        executor = new ConcurrentExecutorImpl(
                DEFAULT_TIMEOUT_SEC, DEFAULT_THREAD_MULTIPLICATION_FACTOR, DEFAULT_MAX_POOL_SIZE,
                THREAD_FACTORY, longRunningProcessor);

        executor.countAsynchronously(input, depth, internalOnly);

        // then
        // exception thrown
    }
}