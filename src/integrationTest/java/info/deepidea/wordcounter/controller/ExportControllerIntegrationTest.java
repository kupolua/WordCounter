package info.deepidea.wordcounter.controller;

import info.deepidea.wordcounter.service.WordCounterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class ExportControllerIntegrationTest {
    private static final String TEXT_COUNT_PARAM_NAME = "textCount";
    private static final String SORTING_ORDER_PARAM_NAME = "sortingOrder";
    private static final String IS_FILTER_WORDS_PARAM_NAME = "isFilterWords";
    public static final String CRAWL_DEPTH = "crawlDepth";
    public static final String CRAWL_SCOPE = "crawlScope";

    @Autowired
    WordCounterService wordCounterService;
    private MockMvc mockMvc;
    private Map<String, Integer> expectedResult;

    @Before
    public void setUp() throws Exception {
        expectedResult = new HashMap<>();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);

        ExportController exportController = new ExportController(wordCounterService);
        mockMvc = MockMvcBuilders.standaloneSetup(exportController).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void testGetPdfResult() throws Exception {
        mockMvc.perform(post("/downloadPDF")
                .param(TEXT_COUNT_PARAM_NAME, "one two two")
                .param(SORTING_ORDER_PARAM_NAME, "VALUE_DESCENDING")
                .param(IS_FILTER_WORDS_PARAM_NAME, "false")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(forwardedUrl("pdfView"))
                .andExpect(model().attributeExists("calculatedWords"))
                .andExpect(view().name("pdfView"))
                .andExpect(model().attribute("calculatedWords", expectedResult));
    }

    @Test
    public void testGetExcelResult() throws Exception {
        mockMvc.perform(post("/downloadExcel")
                .param(TEXT_COUNT_PARAM_NAME, "one two two")
                .param(SORTING_ORDER_PARAM_NAME, "VALUE_DESCENDING")
                .param(IS_FILTER_WORDS_PARAM_NAME, "false")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(forwardedUrl("excelView"))
                .andExpect(model().attributeExists("calculatedWords"))
                .andExpect(view().name("excelView"))
                .andExpect(model().attribute("calculatedWords", expectedResult));
    }

    @Test
    public void testHandleIllegalArgumentException() throws Exception {
        final String errorMsg = "Request is empty.";

        mockMvc.perform(post("/downloadExcel")
                .param(TEXT_COUNT_PARAM_NAME, "")
                .param(SORTING_ORDER_PARAM_NAME, "VALUE_DESCENDING")
                .param(IS_FILTER_WORDS_PARAM_NAME, "false")
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))
                .andExpect(forwardedUrl("error"))
                .andExpect(model().attributeExists("exception"))
                .andExpect(view().name("error"))
                .andExpect(model().attribute("exception", errorMsg));
    }
}