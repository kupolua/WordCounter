package info.deepidea.wordcounter.service;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        final List<ThreadResultContainer> input = null;

        // when
        integrator.integrateResults(input);

        // then
        // thrown exception
    }

    @Test
    public void testIntegrateResults_oneInput() throws Exception {
        // given
        final List<ThreadResultContainer> singleInput = addInput("mouse", 5, null);

        // when
        ThreadResultContainer actual = integrator.integrateResults(singleInput);

        // then
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("mouse", 5);

        assertEquals(expectedMap, actual.getCountedResult());
    }

    @Test
    public void testIntegrateResults_oneInputWithError() throws Exception {
        // given
        final List<ThreadResultContainer> singleInput = addInputWithError(null, "error");

        // when
        ThreadResultContainer actual = integrator.integrateResults(singleInput);

        // then
        Map<String, Integer> expectedMap = Collections.emptyMap();
        List<String> expectedErrorsList = Arrays.asList("error");

        assertEquals(expectedMap, actual.getCountedResult());
        assertEquals(expectedErrorsList, actual.getErrorsList());
    }

    @Test
    public void testIntegrateResults_moreThanOneInput() throws Exception {
        //given
        List<ThreadResultContainer> input = new ArrayList<>();
        input = addInput("mouse", 5, input);
        input = addInput("mouse", 5, input);
        input = addInput("mouse", 6, input);
        input = addInput("world", 1, input);
        input = addInput("world", 1, input);
        input = addInput("big", 1000, input);

        // when
        ThreadResultContainer actual = integrator.integrateResults(input);

        // then
        Map<String, Integer> expected = new HashMap<>();
        expected.put("mouse", 16);
        expected.put("world", 2);
        expected.put("big", 1000);

        assertEquals(expected, actual.getCountedResult());
    }

    @Test
    public void testIntegrateResults_moreThanOneInputWithErrors() throws Exception {
        //given
        List<ThreadResultContainer> input = new ArrayList<>();
        input = addInput("mouse", 5, input);
        input = addInput("mouse", 5, input);
        input = addInputWithError(input, "error3");
        input = addInputWithError(input, "error4");
        input = addInputWithError(input, "error5");
        input = addInputWithError(input, "error6");

        // when
        ThreadResultContainer actual = integrator.integrateResults(input);

        // then
        Map<String, Integer> expectedResultMap = new HashMap<>();
        expectedResultMap.put("mouse", 10);
        List<String> expectedErrorsList = Arrays.asList("error3", "error4", "error5", "error6");

        assertEquals(expectedErrorsList, actual.getErrorsList());
        assertEquals(expectedResultMap, actual.getCountedResult());
    }

    private static List<ThreadResultContainer> addInput(String word, int count,
                                                        List<ThreadResultContainer> existingResult){
        List<ThreadResultContainer> result;

        if (existingResult != null) {
            result = existingResult;
        } else {
            result = new ArrayList<>();
        }

        Map<String, Integer> values = new HashMap<>();
        values.put(word, count);
        result.add(new ThreadResultContainer(values));

        return result;
    }

    private static List<ThreadResultContainer> addInputWithError(List<ThreadResultContainer> existingResult, String error){

        List<ThreadResultContainer> result;

        if (existingResult != null) {
            result = existingResult;
        } else {
            result = new ArrayList<>();
        }

        Map<String, Integer> emptyMap = Collections.emptyMap();
        result.add(new ThreadResultContainer(emptyMap, error));

        return result;
    }
}