package com.qalight.javacourse.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
public class ExportControllerTest {
    @Mock private WordCounterService wordCounterService;
    private WordCounterResultContainer result;
    private MockMvc mockMvc;
    private Map<String, Integer> expectedResult;

    @Before
    public void setUp() throws Exception {
        expectedResult = new HashMap<>();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);
        result = new WordCounterResultContainerImpl(expectedResult);
        ExportController exportController = new ExportController(wordCounterService);
        mockMvc = MockMvcBuilders.standaloneSetup(exportController).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void testGetPdfResult() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post("/downloadPDF")
                .param("textCount", "one two two")
                .param("sortingOrder", "VALUE_DESCENDING")
                .param("isFilterWords", "false"))
                .andExpect(forwardedUrl("pdfView"))
                .andExpect(model().attributeExists("calculatedWords"))
                .andExpect(view().name("pdfView"))
                .andExpect(model().attribute("calculatedWords", expectedResult));

        verify(wordCounterService).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testGetExcelResult() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class))).thenReturn(result);

        mockMvc.perform(post("/downloadExcel")
                .param("textCount", "one two two")
                .param("sortingOrder", "VALUE_DESCENDING")
                .param("isFilterWords", "false"))
                .andExpect(forwardedUrl("excelView"))
                .andExpect(model().attributeExists("calculatedWords"))
                .andExpect(view().name("excelView"))
                .andExpect(model().attribute("calculatedWords", expectedResult));

        verify(wordCounterService).getWordCounterResult(any(CountWordsUserRequest.class));
    }

    @Test
    public void testHandleException() throws Exception {
        when(wordCounterService.getWordCounterResult(any(CountWordsUserRequest.class)))
                .thenThrow(new IllegalArgumentException("test"));

        mockMvc.perform(post("/downloadExcel")
                .param("textCount", "")
                .param("sortingOrder", "VALUE_DESCENDING")
                .param("isFilterWords", "false"))
                .andExpect(forwardedUrl("error"))
                .andExpect(model().attributeExists("exception"))
                .andExpect(view().name("error"))
                .andExpect(model().attribute("exception", "test"));

        verify(wordCounterService).getWordCounterResult(any(CountWordsUserRequest.class));
    }
}