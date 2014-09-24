package com.qalight.javacourse.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
        System.out.println(actual.toString());
        Map<String, Integer> expected = new HashMap<>();
        expected.put("one", 1);
        expected.put("two", 2);
        assertEquals(expected, actual);
    }
}