package com.qalight.javacourse.service;

import com.qalight.javacourse.controller.CountWordsUserRequest;
import com.qalight.javacourse.core.ConcurrentExecutor;
import com.qalight.javacourse.core.WordResultSorter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WordCounterServiceImplTest {
    @Mock private RequestSplitter splitter;
    @Mock private ConcurrentExecutor concurrentExecutor;
    @Mock private CountersIntegrator integrator;
    @Mock private WordFilter filter;
    @Mock private WordResultSorter sorter;
    @Mock private CountWordsUserRequest clientRequest;
    private Map<String, Integer> expectedResult;

    @Before
    public void setUp() throws Exception {
        expectedResult = new HashMap<>();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);
    }

    @Test
    public void testGetWordCounterResult() throws Exception {
        //given
        final String input = "one two two,";

        when(clientRequest.getTextCount()).thenReturn(input);
        when(clientRequest.getSortingOrder()).thenReturn(sorter);
        when(splitter.getSplitRequests(anyString())).thenReturn(new HashSet<>());
        when(concurrentExecutor.countAsynchronously(anyCollection())).thenReturn(new ArrayList<>());
        when(integrator.integrateResults(anyList())).thenReturn(new HashMap<>());
        when(filter.removeUnimportantWords(anyMap(), anyBoolean())).thenReturn(new HashMap<>());
        when(sorter.getSortedWords(anyMap())).thenReturn(expectedResult);

        WordCounterService counterService = new WordCounterServiceImpl(splitter, concurrentExecutor, integrator, filter);

        //when
        WordCounterResultContainer actual = counterService.getWordCounterResult(clientRequest);

        //then
        verify(clientRequest, times(2)).getTextCount();
        verify(clientRequest, times(2)).getSortingOrder();
        verify(splitter).getSplitRequests(anyString());
        verify(concurrentExecutor).countAsynchronously(anyCollection());
        verify(integrator).integrateResults(anyList());
        verify(filter).removeUnimportantWords(anyMap(), anyBoolean());
        verify(sorter).getSortedWords(anyMap());

        Assert.assertEquals(expectedResult, actual.getCountedResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_nullParam() throws Exception {
        //given
        final String input = null;

        when(clientRequest.getTextCount()).thenReturn(input);
        when(clientRequest.getSortingOrder()).thenReturn(sorter);
        when(splitter.getSplitRequests(anyString())).thenReturn(new HashSet<>());
        when(concurrentExecutor.countAsynchronously(anyCollection())).thenReturn(new ArrayList<>());
        when(integrator.integrateResults(anyList())).thenReturn(new HashMap<>());
        when(filter.removeUnimportantWords(anyMap(), anyBoolean())).thenReturn(new HashMap<>());
        when(sorter.getSortedWords(anyMap())).thenReturn(expectedResult);

        WordCounterService counterService = new WordCounterServiceImpl(splitter, concurrentExecutor, integrator, filter);

        //when
        WordCounterResultContainer actual = counterService.getWordCounterResult(clientRequest);

        //then
        verify(clientRequest).getTextCount();
        verifyNoMoreInteractions();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_emptyParam() throws Exception {
        //given
        final String input = "";

        when(clientRequest.getTextCount()).thenReturn(input);
        when(clientRequest.getSortingOrder()).thenReturn(sorter);
        when(splitter.getSplitRequests(anyString())).thenReturn(new HashSet<>());
        when(concurrentExecutor.countAsynchronously(anyCollection())).thenReturn(new ArrayList<>());
        when(integrator.integrateResults(anyList())).thenReturn(new HashMap<>());
        when(filter.removeUnimportantWords(anyMap(), anyBoolean())).thenReturn(new HashMap<>());
        when(sorter.getSortedWords(anyMap())).thenReturn(expectedResult);

        WordCounterService counterService = new WordCounterServiceImpl(splitter, concurrentExecutor, integrator, filter);

        //when
        WordCounterResultContainer actual = counterService.getWordCounterResult(clientRequest);

        //then
        verify(clientRequest).getTextCount();
        verifyNoMoreInteractions();
    }

    @Test(expected = RuntimeException.class)
    public void testGetWordCounterResult_invalidParam() throws Exception {
        //given
        final String input = "http://95.158.60.148:8008/kpl/testingPageINVALID.html";

        when(clientRequest.getTextCount()).thenReturn(input);
        when(clientRequest.getSortingOrder()).thenReturn(sorter);
        when(splitter.getSplitRequests(anyString())).thenReturn(new HashSet<>());
        when(concurrentExecutor.countAsynchronously(anyCollection())).thenThrow(new RuntimeException("test"));
        when(integrator.integrateResults(anyList())).thenReturn(new HashMap<>());
        when(filter.removeUnimportantWords(anyMap(), anyBoolean())).thenReturn(new HashMap<>());
        when(sorter.getSortedWords(anyMap())).thenReturn(expectedResult);

        WordCounterService counterService = new WordCounterServiceImpl(splitter, concurrentExecutor, integrator, filter);

        //when
        WordCounterResultContainer actual = counterService.getWordCounterResult(clientRequest);

        //then
        verify(clientRequest, times(2)).getTextCount();
        verify(clientRequest).getSortingOrder();
        verify(splitter).getSplitRequests(anyString());
        verify(concurrentExecutor).countAsynchronously(anyCollection());
        verifyNoMoreInteractions();
    }
}