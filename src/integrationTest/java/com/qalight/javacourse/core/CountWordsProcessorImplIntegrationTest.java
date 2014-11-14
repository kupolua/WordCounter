package com.qalight.javacourse.core;

import com.qalight.javacourse.service.ThreadResultContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class CountWordsProcessorImplIntegrationTest {

    @Autowired
    CountWordsProcessor processor;

    @Test
    public void testProcess() throws Exception {
        // given
        String input = "one, two,  two";

        // when
        ThreadResultContainer actual = processor.process(input);

        // then
        Map<String, Integer> expected = new HashMap<>();
        expected.put("one", 1);
        expected.put("two", 2);
        assertEquals(expected, actual.getCountedResult());
    }

    @Test(timeout = 120_000)
    public void testProcess_bigPdf() {
        // given
        String input = "http://95.158.60.148:8008/kpl/King-James-Bible-KJV-Bible-PDF.pdf";

        // when
        ThreadResultContainer actual = processor.process(input);


        // then
        assertTrue(actual.getCountedResult().size() > 100);
    }
}