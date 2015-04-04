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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ExportControllerTest {
    private static final String TEXT_COUNT_PARAM_NAME = "textCount";
    private static final String SORTING_ORDER_PARAM_NAME = "sortingOrder";
    private static final String IS_FILTER_WORDS_PARAM_NAME = "isFilterWords";
    private static final String CALCULATED_WORDS = "calculatedWords";
    public static final String CRAWL_DEPTH = "crawlDepth";
    public static final String CRAWL_SCOPE = "crawlScope";

    @Mock private WordCounterService wordCounterService;
    private WordCounterResultContainer result;
    private MockMvc mockMvc;
    private Map<String, Integer> expectedResult;

    @Before
    public void setUp() throws Exception {
        List<String> expectedErrorList = Collections.emptyList();

        expectedResult = new HashMap<String, Integer>();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);

        result = new WordCounterResultContainerImpl(
                expectedResult, expectedErrorList, Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());
        ExportController exportController = new ExportController(wordCounterService);

        mockMvc = MockMvcBuilders.standaloneSetup(exportController).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void testGetPdfResult() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post("/downloadPDF")
                .param(TEXT_COUNT_PARAM_NAME, "one two two")
                .param(SORTING_ORDER_PARAM_NAME, "VALUE_DESCENDING")
                .param(IS_FILTER_WORDS_PARAM_NAME, "false")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(forwardedUrl("pdfView"))
                .andExpect(model().attributeExists(CALCULATED_WORDS))
                .andExpect(view().name("pdfView"))
                .andExpect(model().attribute(CALCULATED_WORDS, expectedResult));

        verify(wordCounterService).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetExcelResult() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post("/downloadExcel")
                .param(TEXT_COUNT_PARAM_NAME, "one two two")
                .param(SORTING_ORDER_PARAM_NAME, "VALUE_DESCENDING")
                .param(IS_FILTER_WORDS_PARAM_NAME, "false")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(forwardedUrl("excelView"))
                .andExpect(model().attributeExists(CALCULATED_WORDS))
                .andExpect(view().name("excelView"))
                .andExpect(model().attribute(CALCULATED_WORDS, expectedResult));

        verify(wordCounterService).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testHandleException() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new IllegalArgumentException("test"));

        mockMvc.perform(post("/downloadExcel")
                .param(TEXT_COUNT_PARAM_NAME, "")
                .param(SORTING_ORDER_PARAM_NAME, "VALUE_DESCENDING")
                .param(IS_FILTER_WORDS_PARAM_NAME, "false")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(forwardedUrl("error"))
                .andExpect(model().attributeExists("exception"))
                .andExpect(view().name("error"))
                .andExpect(model().attribute("exception", "test"));

        verify(wordCounterService).getWordCounterResult(any(CountWordsUserRequest.class));
    }
}