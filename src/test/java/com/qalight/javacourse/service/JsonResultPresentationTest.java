package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class JsonResultPresentationTest {
    private JsonResultPresentation jsonResultPresentation;

    @Before
    public void setUp() {
        jsonResultPresentation = new JsonResultPresentation();
    }

    @Test
    public void isEligible_json() {
        //given
        final String TYPE = "json";

        //when
        boolean actualResult = jsonResultPresentation.isEligible(TYPE);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void isEligible_gif() {
        //given
        final String TYPE = "gif";

        //when
        boolean actualResult = jsonResultPresentation.isEligible(TYPE);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testCreateResponse() throws Exception {
        //given
        final String expectedJsonResponse = "{\"success\":true,\"filteredWords\":[[\"Project\",\"24\"],[\"Word\",\"13\"],[\"Counter\",\"5\"],[\"Hello\",\"10\"],[\"World\",\"7\"]],\"unFilteredWords\":[[\"Project\",\"24\"],[\"Word\",\"13\"],[\"Counter\",\"5\"],[\"Hello\",\"10\"],[\"World\",\"7\"]]}";
        final Map<String, Integer> filteredCountedWords = new HashMap<>();
        filteredCountedWords.put("Hello", 10);
        filteredCountedWords.put("World", 7);
        filteredCountedWords.put("Word", 13);
        filteredCountedWords.put("Counter", 5);
        filteredCountedWords.put("Project", 24);
        final Map<String, Integer> unFilteredCountedWords = new HashMap<>();
        unFilteredCountedWords.put("Hello", 10);
        unFilteredCountedWords.put("World", 7);
        unFilteredCountedWords.put("Word", 13);
        unFilteredCountedWords.put("Counter", 5);
        unFilteredCountedWords.put("Project", 24);

        //when
        String actualJsonResponse = jsonResultPresentation.createResponse(filteredCountedWords, unFilteredCountedWords);

        //then
        Assert.assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    public void testCreateErrorResponse() throws Exception {
        //given
        String expectedJsonResponse = "{\"success\":false,\"errorMessageToUser\":";

        //when
        boolean isExpectedJsonResponse = jsonResultPresentation.createErrorResponse("Your request is empty.").startsWith(expectedJsonResponse);

        //then
        Assert.assertTrue(isExpectedJsonResponse);
    }
}