package com.qalight.javacourse.service;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CountersIntegratorImplTest {
    private CountersIntegrator integrator;

    @Before
    public void setup() {
        integrator = new CountersIntegratorImpl();
    }

    @Test(expected = IllegalArgumentException.class)
     public void testIntegrateResults_nullCollection() throws Exception {
        // given
        final List<Map<String, Integer>> input = null;

        // when
        integrator.integrateResults(input);

        // then
        // thrown exception
    }

    @Test
    public void testIntegrateResults_oneInput() throws Exception {
        // given
        final List<Map<String, Integer>> singleInput = addInput("mouse", 5, null);

        // when
        Map<String, Integer> actual = integrator.integrateResults(singleInput);

        // then
        Map<String, Integer> expected = new HashMap<>();
        expected.put("mouse", 5);

        assertEquals(expected, actual);
    }

    @Test
    public void testIntegrateResults_moreThanOneInput() throws Exception {
        List<Map<String, Integer>> input = new ArrayList<>();
        input = addInput("mouse", 5, input);
        input = addInput("mouse", 5, input);
        input = addInput("mouse", 6, input);
        input = addInput("world", 1, input);
        input = addInput("world", 1, input);
        input = addInput("big", 1000, input);

        // when
        Map<String, Integer> actual = integrator.integrateResults(input);

        // then
        Map<String, Integer> expected = new HashMap<>();
        expected.put("mouse", 16);
        expected.put("world", 2);
        expected.put("big", 1000);

        assertEquals(expected, actual);
    }

    private static List<Map<String, Integer>> addInput(String word, int count, List<Map<String, Integer>> existingResult){
        List<Map<String, Integer>> result;

        if (existingResult != null) {
            result = existingResult;
        } else {
            result = new ArrayList<>();
        }

        Map<String, Integer> values = new HashMap<>();
        values.put(word, count);
        result.add(values);

        return result;
    }
}