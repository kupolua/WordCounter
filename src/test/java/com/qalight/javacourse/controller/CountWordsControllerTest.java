package com.qalight.javacourse.controller;

import com.qalight.javacourse.service.WordCounterResultContainer;
import com.qalight.javacourse.service.WordCounterResultContainerImpl;
import com.qalight.javacourse.service.WordCounterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CountWordsControllerTest {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String COUNT_WORDS = "/countWords";
    private static final String COUNT_WORDS_WITH_PARAMS = "/countWordsWithParams";

    @Mock private WordCounterService wordCounterService;
    private WordCounterResultContainer result;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        List expectedErrorList = Collections.emptyList();
        Map<String, Integer> expectedResult = new HashMap();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);
        result = new WordCounterResultContainerImpl(expectedResult, expectedErrorList);
        CountWordsController controller = new CountWordsController(wordCounterService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetResultWithoutError() throws Exception {
        final String expectedBody = "{\"countedResult\":{\"one\":1,\"two\":2},\"errors\":[]}";

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post(COUNT_WORDS)
                .param("textCount", "one two two"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetResultWithError() throws Exception {
        final String expectedBody = "{\"countedResult\":{},\"errors\":[\"Error has occurred.\",\"ERROR!!!\"]}";
        final Map<String, Integer> expectedEmptyMap = Collections.emptyMap();
        final List expectedErrorList = Arrays.asList("Error has occurred.", "ERROR!!!");
        result = new WordCounterResultContainerImpl(expectedEmptyMap, expectedErrorList);

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post(COUNT_WORDS)
                .param("textCount", "http://some-nonexistent-site.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testHandleIllegalArgumentExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new IllegalArgumentException("test"));

        mockMvc.perform(post(COUNT_WORDS)
                .param("textCount", ""))
                .andExpect(status().isBadRequest());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testHandleRuntimeExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new RuntimeException("test"));

        mockMvc.perform(post(COUNT_WORDS)
                .param("textCount", "http://some-nonexistent-site.com"))
                .andExpect(status().isInternalServerError());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetResultWithParams_WithoutError() throws Exception {
        final String expectedBody = "{\"countedResult\":{\"one\":1,\"two\":2},\"errors\":[]}";

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post(COUNT_WORDS_WITH_PARAMS)
                .param("textCount", "one two two")
                .param("sortingOrder", "KEY_ASCENDING")
                .param("isFilterWords", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetResultWithParams_WithError() throws Exception {
        final String expectedBody = "{\"countedResult\":{},\"errors\":[\"Error has occurred.\",\"ERROR!!!\"]}";
        final Map<String, Integer> expectedEmptyMap = Collections.emptyMap();
        final List expectedErrorList = Arrays.asList("Error has occurred.", "ERROR!!!");
        result = new WordCounterResultContainerImpl(expectedEmptyMap, expectedErrorList);

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post(COUNT_WORDS_WITH_PARAMS)
                .param("textCount", "http://some-nonexistent-site.com")
                .param("sortingOrder", "KEY_ASCENDING")
                .param("isFilterWords", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetResultWithParams_HandleIllegalArgumentExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new IllegalArgumentException("test"));

        mockMvc.perform(post(COUNT_WORDS_WITH_PARAMS)
                .param("textCount", "")
                .param("sortingOrder", "KEY_ASCENDING")
                .param("isFilterWords", "true"))
                .andExpect(status().isBadRequest());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetResultWithParams_HandleRuntimeExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new RuntimeException("test"));

        mockMvc.perform(post(COUNT_WORDS_WITH_PARAMS)
                .param("textCount", "http://some-nonexistent-site.com")
                .param("sortingOrder", "KEY_ASCENDING")
                .param("isFilterWords", "true"))
                .andExpect(status().isInternalServerError());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }
}