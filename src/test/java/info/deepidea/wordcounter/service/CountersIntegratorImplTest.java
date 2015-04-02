package info.deepidea.wordcounter.service;

import org.junit.Assert;
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
        final List<ThreadResultContainer> singleInput = addInput("bbc.com", "mouse", 5, null);

        // when
        ThreadResultContainer actual = integrator.integrateResults(singleInput);

        // then
        Map<String, Integer> expectedWords = new HashMap<>();
        expectedWords.put("mouse", 5);

        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 21);
            put("statisticUniqueWords", 3);
            put("statisticTotalCharacters", 23);
            put("statisticTotalWords", 3);
        }};

        Map<String, Set<String>> expectedLinks = new HashMap<String, Set<String>>() {{
            put("bbc.com", new HashSet<>(Arrays.asList("bbc.com/tv")));
        }};

        assertEquals(expectedWords, actual.getCountedResult());
        assertEquals(expectedStatistic, actual.getWordStatistic());
        assertEquals(expectedLinks, actual.getRelatedLinks());
    }

    @Test
    public void testIntegrateResults_oneInputWithError() throws Exception {
        // given
        final List<ThreadResultContainer> singleInput = addInputWithError("boob1c.com", null, "error");

        // when
        ThreadResultContainer actual = integrator.integrateResults(singleInput);

        // then
        Map<String, Integer> expectedMap = Collections.emptyMap();
        List<String> expectedErrorsList = Arrays.asList("error");

        assertEquals(expectedMap, actual.getCountedResult());
        assertEquals(expectedErrorsList, actual.getErrorsList());
        Assert.assertTrue(actual.getRelatedLinks().isEmpty());
    }

    @Test
    public void testIntegrateResults_moreThanOneInput() throws Exception {
        //given
        List<ThreadResultContainer> input = new ArrayList<>();
        input = addInput("bbc.com", "mouse", 5, input);
        input = addInput("bbc.com", "mouse", 5, input);
        input = addInput("bbc.com", "mouse", 5, input);
        input = addInput("cnn.com", "world", 1, input);
        input = addInput("cnn.com", "world", 1, input);
        input = addInput("pravda.com.ua", "big", 1000, input);

        // when
        ThreadResultContainer actual = integrator.integrateResults(input);

        // then
        Map<String, Integer> expected = new HashMap<>();
        expected.put("mouse", 5);
        expected.put("world", 1);
        expected.put("big", 1000);

        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 63);
            put("statisticUniqueWords", 9);
            put("statisticTotalCharacters", 69);
            put("statisticTotalWords", 9);
        }};

        assertEquals(expected, actual.getCountedResult());
        assertEquals(expectedStatistic, actual.getWordStatistic());
    }
//
    @Test
    public void testIntegrateResults_moreThanOneInputWithErrors() throws Exception {
        //given
        List<ThreadResultContainer> input = new ArrayList<>();
        input = addInput("bbc.com", "mouse", 5, input);
        input = addInput("bbc.com", "mouse", 5, input);
        input = addInputWithError("boobc2.com", input, "error3");
        input = addInputWithError("gugol.com", input, "error4");
        input = addInputWithError("d766.com", input, "error5");
        input = addInputWithError("hobr.com", input, "error6");

        // when
        ThreadResultContainer actual = integrator.integrateResults(input);

        // then
        final Map<String, Integer> expectedStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 21);
            put("statisticUniqueWords", 3);
            put("statisticTotalCharacters", 23);
            put("statisticTotalWords", 3);
        }};

        Map<String, Integer> expectedResultMap = new HashMap<>();
        expectedResultMap.put("mouse", 5);

        List<String> expectedErrorsList = Arrays.asList("error3", "error4", "error5", "error6");

        assertEquals(expectedErrorsList, actual.getErrorsList());
        assertEquals(expectedResultMap, actual.getCountedResult());
        assertEquals(expectedStatistic, actual.getWordStatistic());
    }

    private static List<ThreadResultContainer> addInput(String clientRequest, String word, int count,
                                                        List<ThreadResultContainer> existingResult){
        final Map<String, Integer> wordStatistic = new HashMap<String, Integer>() {{
            put("statisticСharactersWithoutSpaces", 21);
            put("statisticUniqueWords", 3);
            put("statisticTotalCharacters", 23);
            put("statisticTotalWords", 3);
        }};

        List<ThreadResultContainer> result;

        if (existingResult != null) {
            result = existingResult;
        } else {
            result = new ArrayList<>();
        }

        Map<String, Integer> values = new HashMap<>();
        values.put(word, count);

        result.add(new ThreadResultContainer(values, wordStatistic, getRelatedLinks(clientRequest), clientRequest));

        return result;
    }

    private static List<ThreadResultContainer> addInputWithError(String clientRequest, List<ThreadResultContainer> existingResult, String error){
        List<ThreadResultContainer> result;

        if (existingResult != null) {
            result = existingResult;
        } else {
            result = new ArrayList<>();
        }

        result.add(new ThreadResultContainer(error));

        return result;
    }

    private static Map<String, Set<String>> getRelatedLinks(String clientRequest) {
        Set<String> gainedLinks = new HashSet<>(Arrays.asList("bbc.com/tv"));
        Map<String, Set<String>> relatedLinks = new HashMap<>();
        relatedLinks.put(clientRequest, gainedLinks);
        return relatedLinks;
    }

    private static Map<String, Set<String>> getEmptyRelatedLinks(String clientRequest) {
        Map<String, Set<String>> relatedLinks = new HashMap<>();
        relatedLinks.put(clientRequest, Collections.emptySet());
        return relatedLinks;
    }
}