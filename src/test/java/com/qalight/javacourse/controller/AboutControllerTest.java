package com.qalight.javacourse.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class AboutControllerTest {
    private static final String VERSION_FIELD_NAME = "version";
    private static final String VERSION_FIELD_VALUE = "0.7";

    private MockMvc mockMvc;

    @Before
    public void setup() {
        AboutController controller = new AboutController();
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        ReflectionTestUtils.setField(controller, VERSION_FIELD_NAME, VERSION_FIELD_VALUE, String.class);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver).build();
    }

    @Test
    public void testAbout() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"))
                .andExpect(forwardedUrl("/WEB-INF/view/about.jsp"))
                .andExpect(model().attribute(VERSION_FIELD_NAME, VERSION_FIELD_VALUE));
    }
}