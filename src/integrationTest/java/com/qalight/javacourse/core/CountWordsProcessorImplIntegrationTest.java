package com.qalight.javacourse.core;

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
        Map<String, Integer> actual = processor.process(input);

        // then
        Map<String, Integer> expected = new HashMap<>();
        expected.put("one", 1);
        expected.put("two", 2);
        assertEquals(expected, actual);
    }

    @Test(timeout = 120_000)
    public void testProcess_bigPdf() {
        // given
        String input = "http://d23a3s5l1qjyz.cloudfront.net/wp-content/uploads/2012/09/King-James-Bible-KJV-Bible-PDF.pdf";

        // when
        Map<String, Integer> actual = processor.process(input);


        // then
        assertTrue(actual.size() > 100);
    }
}