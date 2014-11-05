package com.qalight.javacourse.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.*;

import com.qalight.javacourse.service.JsonResultPresentation;
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

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CountWordsControllerTest {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Mock private WordCounterService wordCounterService;
    @Mock private JsonResultPresentation resultPresentation;
    private WordCounterResultContainer result;
    private MockMvc mockMvc;
    private Map<String, Integer> expectedResult;

    @Before
    public void setUp() throws Exception {
        expectedResult = new HashMap();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);
        result = new WordCounterResultContainerImpl(expectedResult);
        CountWordsController controller = new CountWordsController(wordCounterService, resultPresentation);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetResult() throws Exception {
        final String expectedBody = "{\"success\":true,\"unFilteredWords\":[[\"two\",\"2\"],[\"one\",\"1\"]]}";

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);
        when(resultPresentation.createResponse(anyMap())).thenReturn(expectedBody);

        mockMvc.perform(post("/countWords")
                .param("textCount", "one two two"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("success", is(true)))
                .andExpect(jsonPath("unFilteredWords").exists())
                .andExpect(content().string(expectedBody));

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
        verify(resultPresentation, times(1)).createResponse(anyMap());
    }

    @Test
    public void testGetResultRestStyle() throws Exception {
        final String expectedBody = "{\"countedResult\":{\"one\":1,\"two\":2}}";

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post("/countWordsRestStyle")
                .param("textCount", "one two two"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("countedResult", is(expectedResult)))
                .andExpect(content().string(expectedBody));

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testHandleIllegalArgumentExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new IllegalArgumentException("test"));

        mockMvc.perform(post("/countWordsRestStyle")
                .param("textCount", ""))
                .andExpect(status().isBadRequest());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testHandleRuntimeExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new RuntimeException("test"));

        mockMvc.perform(post("/countWordsRestStyle")
                .param("textCount", "http://some-nonexistent-site.com"))
                .andExpect(status().isInternalServerError());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }
}