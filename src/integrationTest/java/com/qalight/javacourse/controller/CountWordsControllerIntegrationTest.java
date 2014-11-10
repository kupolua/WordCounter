package com.qalight.javacourse.controller;

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

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class CountWordsControllerIntegrationTest {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    @Autowired private CountWordsController countWordsController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(countWordsController).build();
    }

    @Test
    public void testGetResult_QUESTION_ABOUT_CODE_STYLE() throws Throwable {
        // given
        final String expectedBody = "{\"success\":true,\"unFilteredWords\":[[\"two\",\"2\"],[\"the\",\"1\"]]}";

        // when
        mockMvc.perform(post("/countWords")
                .param("textCount", "The TwO two-"))

        // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("success", is(true)))
                .andExpect(jsonPath("unFilteredWords").exists())
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testGetResult2() throws Throwable {
        // given
        final String expectedBody = "{\"success\":true,\"unFilteredWords\":[[\"two\",\"2\"],[\"the\",\"1\"]]}";

        // when
        mockMvc.perform(post("/countWords")
                .param("textCount", "The TwO two-"))

        // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("success", is(true)))
                .andExpect(jsonPath("unFilteredWords").exists())
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testGetResultRestStyle() throws Exception {
        // given
        final String givenText = "The TwO two-";
        final String expectedBody = "{\"countedResult\":{\"two\":2,\"the\":1}}";
        final Map<String, Integer> expectedResult = new HashMap() {{
            put("the", 1);
            put("two", 2);
        }};

        // when
        mockMvc.perform(post("/countWordsRestStyle")
                .param("textCount", givenText))

        // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("countedResult", is(expectedResult)))
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testHandleIllegalArgumentExceptions() throws Exception {
        // given
        final String givenText = "";
        final String expectedBody = "{\"respMessage\":\"Request is null or empty\"}";
        final String expectedRespMessage = "Request is null or empty";

        // when
        mockMvc.perform(post("/countWords").param("textCount", givenText))

        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("respMessage", is(expectedRespMessage)))
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testHandleRuntimeExceptions() throws Exception {
        // given
        final String givenText = null;

        // when
        mockMvc.perform(post("/countWords").param("textCount", givenText))

        // then
                .andExpect(status().isBadRequest());
    }
}