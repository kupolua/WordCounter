package com.qalight.javacourse.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class IndexControllerIntegrationTest {
    private static final String VERSION_FIELD_NAME = "version";
    private static final String FILTER_FIELD_NAME = "filter";
    private @Value("${app.version}") String appVersion;
    private @Value("${wordsEN}") String wordsEn;
    private @Value("${wordsRU}") String wordsRu;
    private @Value("${wordsUA}") String wordsUa;
    private String filterField;
    private MockMvc mockMvc;
    @Autowired private IndexController indexController;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        this.filterField = getWordsForFilter(wordsEn, wordsRu, wordsUa);
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("index"))
                .andExpect(model().attribute(VERSION_FIELD_NAME, appVersion))
                .andExpect(model().attribute(FILTER_FIELD_NAME, filterField));
    }

    private String getWordsForFilter(String... languages) {
        StringBuilder wordsForFilter = new StringBuilder();
        for (int i = 0; i < languages.length; i++) {
            wordsForFilter.append(languages[i]);
            if (i < (languages.length - 1)) {
                wordsForFilter.append(" ");
            }
        }
        return String.valueOf(wordsForFilter);
    }
}