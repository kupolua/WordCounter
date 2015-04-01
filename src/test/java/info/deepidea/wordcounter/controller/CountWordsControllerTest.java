package info.deepidea.wordcounter.controller;

import info.deepidea.wordcounter.service.WordCounterResultContainer;
import info.deepidea.wordcounter.service.WordCounterResultContainerImpl;
import info.deepidea.wordcounter.service.WordCounterService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CountWordsControllerTest {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String COUNT_WORDS = "/countWords";
    private static final String COUNT_WORDS_WITH_PARAMS = "/countWordsWithParams";
    public static final String TEXT_COUNT = "textCount";
    public static final String CRAWL_DEPTH = "crawlDepth";
    public static final String CRAWL_SCOPE = "crawlScope";

    @Mock private WordCounterService wordCounterService;
    private WordCounterResultContainer result;
    private MockMvc mockMvc;
    private Map<String, Integer> wordStatistic;

    @Before
    public void setUp() throws Exception {
        List<String> expectedErrorList = Collections.emptyList();
        Map<String, Set<String>> urls = Collections.emptyMap();

        wordStatistic = new HashMap<>();
        wordStatistic.put("statisticСharactersWithoutSpaces", 9);
        wordStatistic.put("statisticUniqueWords", 2);
        wordStatistic.put("statisticTotalCharacters", 11);
        wordStatistic.put("statisticTotalWords", 3);

        Map<String, Integer> expectedResult = new HashMap();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);

        result = new WordCounterResultContainerImpl(expectedResult, expectedErrorList, wordStatistic, urls, Collections.emptyMap());
        CountWordsController controller = new CountWordsController(wordCounterService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetResultWithoutError() throws Exception {
        final String expectedBody = "{\"countedResult\":{\"one\":1,\"two\":2},\"errors\":[],\"wordStatistic\":{\"statisticСharactersWithoutSpaces\":9,\"statisticUniqueWords\":2,\"statisticTotalCharacters\":11,\"statisticTotalWords\":3},\"relatedLinks\":{},\"d3TestData\":{}}";

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post(COUNT_WORDS)
                .param(TEXT_COUNT, "one two two")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody)).andDo(print());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetErrorResult() throws Exception {
        final String expectedBody = "{\"countedResult\":{},\"errors\":[\"Error has occurred.\",\"ERROR!!!\"],\"wordStatistic\":{},\"relatedLinks\":{},\"d3TestData\":{}}";

        final List<String> expectedErrorList = Arrays.asList("Error has occurred.", "ERROR!!!");

        result = new WordCounterResultContainerImpl(
                Collections.emptyMap(), expectedErrorList, Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post(COUNT_WORDS)
                .param(TEXT_COUNT, "http://some-nonexistent-site.com")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody)).andDo(print());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testHandleIllegalArgumentExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new IllegalArgumentException("test"));

        mockMvc.perform(post(COUNT_WORDS)
                .param(TEXT_COUNT, "")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(status().isBadRequest());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testHandleRuntimeExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new RuntimeException("test"));

        mockMvc.perform(post(COUNT_WORDS)
                .param(TEXT_COUNT, "http://some-nonexistent-site.com")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(status().isInternalServerError());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetResultWithParams_WithoutError() throws Exception {
        final String expectedBody = "{\"countedResult\":{\"one\":1,\"two\":2},\"errors\":[],\"wordStatistic\":{\"statisticСharactersWithoutSpaces\":9,\"statisticUniqueWords\":2,\"statisticTotalCharacters\":11,\"statisticTotalWords\":3},\"relatedLinks\":{},\"d3TestData\":{}}";

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post(COUNT_WORDS_WITH_PARAMS)
                .param(TEXT_COUNT, "one two two")
                .param("sortingOrder", "KEY_ASCENDING")
                .param("isFilterWords", "true")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetResultWithParams_error() throws Exception {
        final String expectedBody = "{\"countedResult\":{},\"errors\":[\"Error has occurred.\",\"ERROR!!!\"],\"wordStatistic\":{},\"relatedLinks\":{},\"d3TestData\":{}}";

        final List expectedErrorList = Arrays.asList("Error has occurred.", "ERROR!!!");
        result = new WordCounterResultContainerImpl(
                Collections.emptyMap(), expectedErrorList, Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());

        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post(COUNT_WORDS_WITH_PARAMS)
                .param(TEXT_COUNT, "http://some-nonexistent-site.com")
                .param("sortingOrder", "KEY_ASCENDING")
                .param("isFilterWords", "true")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
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
                .param(TEXT_COUNT, "")
                .param("sortingOrder", "KEY_ASCENDING")
                .param("isFilterWords", "true")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(status().isBadRequest());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetResultWithParams_HandleRuntimeExceptions() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new RuntimeException("test"));

        mockMvc.perform(post(COUNT_WORDS_WITH_PARAMS)
                .param(TEXT_COUNT, "http://some-nonexistent-site.com")
                .param("sortingOrder", "KEY_ASCENDING")
                .param("isFilterWords", "true")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(status().isInternalServerError());

        verify(wordCounterService, times(1)).getWordCounterResult(any(CountWordsUserRequest.class));
    }
}