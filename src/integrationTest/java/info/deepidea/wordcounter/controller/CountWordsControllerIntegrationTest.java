package info.deepidea.wordcounter.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class CountWordsControllerIntegrationTest {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String COUNT_URL_ENDING = "/countWords";
    private static final String COUNT_URL_ENDING_WITH_PARAMS = "/countWordsWithParams";
    public static final String CRAWL_DEPTH = "crawlDepth";
    public static final String CRAWL_SCOPE = "crawlScope";

    @Autowired private CountWordsController countWordsController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(countWordsController).build();
    }

    @Test
    public void testGetResult_withError() throws Exception {
        // given
        final String givenText = "https://dl.dropboxusercontent.com/u/12495182/tests/woddfrds.pdf";
        final String expectedBody = "{\"countedResult\":{},\"errors\":[\"Cannot connect to the source: " +
                ">https://dl.dropboxusercontent.com/u/12495182/tests/woddfrds.pdf\"],\"wordStatistic\":{}," +
                "\"relatedLinks\":{}}";

        // when
        mockMvc.perform(post(COUNT_URL_ENDING)
                .param("textCount", givenText)
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testGetResult_withoutError() throws Exception {
        // given
        final String givenText = "one two two";
        final String expectedBody = "{\"countedResult\":{\"two\":2,\"one\":1},\"errors\":[],\"" +
                "wordStatistic\":{\"statisticCharactersWithoutSpaces\":9,\"statisticUniqueWords\":2," +
                "\"statisticTotalCharacters\":11,\"statisticTotalWords\":3},\"relatedLinks\":{}}";

        // when
        mockMvc.perform(post(COUNT_URL_ENDING)
                .param("textCount", givenText)
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testHandleIllegalArgumentExceptions() throws Exception {
        // given
        final String givenText = "";
        final String expectedBody = "{\"respMessage\":\"Request is empty.\"}";

        // when
        mockMvc.perform(post(COUNT_URL_ENDING)
                .param("textCount", givenText)
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testGetResultWithParams_withError() throws Exception {
        // given
        final String givenText = "https://dl.dropboxusercontent.com/u/12495182/tests/woddfrds.pdf";
        final String sortingOrder = "KEY_ASCENDING";
        final String isFilterWords = "true";
        final String expectedBody = "{\"countedResult\":{},\"errors\":[\"Cannot connect to the source: " +
                ">https://dl.dropboxusercontent.com/u/12495182/tests/woddfrds.pdf\"],\"wordStatistic\":{}" +
                ",\"relatedLinks\":{}}";

        // when
        mockMvc.perform(post(COUNT_URL_ENDING_WITH_PARAMS)
                .param("textCount", givenText)
                .param("sortingOrder", sortingOrder)
                .param("isFilterWords", isFilterWords)
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testGetResultWithParams_withoutError() throws Exception {
        // given
        final String givenText = "The bill apple Bill";
        final String sortingOrder = "KEY_ASCENDING";
        final String isFilterWords = "true";
        final String expectedBody = "{\"countedResult\":{\"apple\":1,\"bill\":2},\"errors\":[],\"" +
                "wordStatistic\":{\"statisticCharactersWithoutSpaces\":16,\"statisticUniqueWords\":3," +
                "\"statisticTotalCharacters\":19,\"statisticTotalWords\":4},\"relatedLinks\":{}}";

        // when
        mockMvc.perform(post(COUNT_URL_ENDING_WITH_PARAMS)
                .param("textCount", givenText)
                .param("sortingOrder", sortingOrder)
                .param("isFilterWords", isFilterWords)
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testGetResultWithParams_HandleIllegalArgumentExceptions() throws Exception {
        // given
        final String givenText = "";
        final String sortingOrder = "KEY_ASCENDING";
        final String isFilterWords = "true";
        final String expectedBody = "{\"respMessage\":\"Request is empty.\"}";

        // when
        mockMvc.perform(post(COUNT_URL_ENDING_WITH_PARAMS).param(
                "textCount", givenText)
                .param("sortingOrder", sortingOrder)
                .param("isFilterWords", isFilterWords)
                .param(CRAWL_DEPTH, "0")
                .param(CRAWL_SCOPE, "false"))

                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedBody));
    }
}