package com.qalight.javacourse.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void testGetResultRestStyle_withError() throws Exception {
        // given
        final String givenText = "https://dl.dropboxusercontent.com/u/12495182/tests/woddfrds.pdf";
        final String expectedBody = "{\"countedResult\":{}," +
                "\"errors\":[\"Can't connect to https://dl.dropboxusercontent.com/u/12495182/tests/woddfrds.pdf\"]}";

        // when
        mockMvc.perform(post("/countWordsRestStyle")
                .param("textCount", givenText))

        // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testGetResultRestStyle_withoutError() throws Exception {
        // given
        final String givenText = "one two two";
        final String expectedBody = "{\"countedResult\":{\"two\":2,\"one\":1},\"errors\":[]}";

        // when
        mockMvc.perform(post("/countWordsRestStyle")
                .param("textCount", givenText))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(expectedBody));
    }

    @Test
    public void testHandleIllegalArgumentExceptions() throws Exception {
        // given
        final String givenText = "";
        final String expectedBody = "{\"respMessage\":\"Request is null or empty\"}";

        // when
        mockMvc.perform(post("/countWordsRestStyle").param("textCount", givenText))

        // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedBody)).andDo(print());
    }
}