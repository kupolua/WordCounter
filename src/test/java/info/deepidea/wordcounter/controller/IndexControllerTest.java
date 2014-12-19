package info.deepidea.wordcounter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class IndexControllerTest {
    private static final String VERSION_FIELD_NAME = "version";
    private static final String VERSION_FIELD_VALUE = "0.7";
    private static final String FILTER_FIELD_NAME = "filter";
    private static final String FILTER_FIELD_VALUE = "the a an to and of";

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        IndexController controller = new IndexController();
        ReflectionTestUtils.setField(controller, VERSION_FIELD_NAME, VERSION_FIELD_VALUE, String.class);
        ReflectionTestUtils.setField(controller, FILTER_FIELD_NAME, FILTER_FIELD_VALUE, String.class);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("index"))
                .andExpect(model().attribute(VERSION_FIELD_NAME, VERSION_FIELD_VALUE))
                .andExpect(model().attribute(FILTER_FIELD_NAME, FILTER_FIELD_VALUE));
    }
}