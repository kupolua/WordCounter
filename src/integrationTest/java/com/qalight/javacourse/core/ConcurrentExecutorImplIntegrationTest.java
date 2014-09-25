package com.qalight.javacourse.core;

import com.qalight.javacourse.util.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class ConcurrentExecutorImplIntegrationTest {

    @Autowired
    private ConcurrentExecutor executor;

    @Test
    public void testCountAsynchronously_singleInput(){
        // given
        Set<String> input = new HashSet<>();
        input.add("word word, word");

        // when
        List<Map<String, Integer>> actual = executor.countAsynchronously(input);

        // then
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("word", 3);

        assertEquals(1, actual.size());
        assertEquals(expectedResult, actual.get(0));
    }

    @Test
    public void testCountAsynchronously_multipleInput(){
        // given
        Set<String> input = new HashSet<>();
        input.add("word word, word");
        input.add("hello hello");
        input.add("jump");

        // when
        List<Map<String, Integer>> actual = executor.countAsynchronously(input);

        // then
        Map<String, Integer> expectedResult0 = new HashMap<>();
        expectedResult0.put("word", 3);

        Map<String, Integer> expectedResult1 = new HashMap<>();
        expectedResult1.put("hello", 2);

        Map<String, Integer> expectedResult2 = new HashMap<>();
        expectedResult2.put("jump", 1);

        List<Map<String, Integer>> expected = new ArrayList<>();
        expected.add(expectedResult0);
        expected.add(expectedResult1);
        expected.add(expectedResult2);

        assertEquals(3, actual.size());
        assertTrue(Assertions.equalCollections(expected, actual));
    }
}