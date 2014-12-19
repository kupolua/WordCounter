package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.controller.CountWordsUserRequest;
import info.deepidea.wordcounter.core.ConcurrentExecutor;
import info.deepidea.wordcounter.core.WordResultSorter;
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
    @Mock private ThreadResultContainer threadContainer;
    private Map<String, Integer> expectedResult;

    @Before
    public void setUp() throws Exception {
        expectedResult = new HashMap<>();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);

        when(clientRequest.getSortingOrder()).thenReturn(sorter);
        when(splitter.getSplitRequests(anyString())).thenReturn(new HashSet<>());
        when(concurrentExecutor.countAsynchronously(anyCollection())).thenReturn(new ArrayList<>());
        when(integrator.integrateResults(anyList())).thenReturn(threadContainer);
        when(filter.removeUnimportantWords(anyMap(), anyBoolean())).thenReturn(new HashMap<>());
    }

    @Test
    public void testGetWordCounterResult() throws Exception {
        //given
        final String input = "one two two,";
        final List expectedErrorList = Collections.emptyList();

        when(clientRequest.getTextCount()).thenReturn(input);
        when(sorter.getSortedWords(anyMap())).thenReturn(expectedResult);
        when(threadContainer.getErrorsList()).thenReturn(expectedErrorList);

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
        Assert.assertEquals(expectedErrorList, actual.getErrors());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWordCounterResult_nullParam() throws Exception {
        //given
        final String input = null;

        when(clientRequest.getTextCount()).thenReturn(input);
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
        when(sorter.getSortedWords(anyMap())).thenReturn(expectedResult);

        WordCounterService counterService = new WordCounterServiceImpl(splitter, concurrentExecutor, integrator, filter);

        //when
        WordCounterResultContainer actual = counterService.getWordCounterResult(clientRequest);

        //then
        verify(clientRequest).getTextCount();
        verifyNoMoreInteractions();
    }

    @Test
    public void testGetWordCounterResult_invalidParam() throws Exception {
        //given
        final String input = "http://95.158.60.148:8008/kpl/testingPageINVALID.html";

        final List expectedErrorList = Arrays.asList("Error has occurred.");
        final Map<String, Integer> expectedEmptyMap = Collections.emptyMap();

        when(clientRequest.getTextCount()).thenReturn(input);
        when(sorter.getSortedWords(anyMap())).thenReturn(expectedEmptyMap);
        when(threadContainer.getErrorsList()).thenReturn(expectedErrorList);

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
        verify(threadContainer).getErrorsList();

        Assert.assertEquals(expectedEmptyMap, actual.getCountedResult());
        Assert.assertEquals(expectedErrorList, actual.getErrors());
    }
}