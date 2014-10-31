package com.qalight.javacourse.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountWordsTaskTest {
    @Mock private CountWordsProcessor wordsProcessor;

    @Test
    public void testCall() throws Exception {
        //given
        final String clientRequest = "one two two,";

        final Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);
        when(wordsProcessor.process(anyString())).thenReturn(expectedResult);

        CountWordsTask wordsTask = new CountWordsTask(clientRequest, wordsProcessor);

        //when
        final Map<String, Integer> actualResult = wordsTask.call();

        //then
        verify(wordsProcessor, times(1)).process(anyString());

        assertEquals(expectedResult, actualResult);
    }
}