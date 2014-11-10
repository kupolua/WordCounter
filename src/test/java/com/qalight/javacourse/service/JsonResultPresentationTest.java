package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonResultPresentationTest {
    private JsonResultPresentation jsonResultPresentation;

    @Before
    public void setUp() {
        jsonResultPresentation = new JsonResultPresentation();
    }

    @Test
    public void testCreateResponse_validMap() throws Exception {
        // given
        final String expectedJsonResponse = "{\"success\":true,\"unFilteredWords\":[[\"Project\",\"24\"],[\"Word\",\"13\"],[\"Counter\",\"5\"],[\"Hello\",\"10\"],[\"World\",\"7\"]]}";

        final Map<String, Integer> unFilteredCountedWords = new HashMap<String, Integer>() {{
            put("Hello", 10);
            put("World", 7);
            put("Word", 13);
            put("Counter", 5);
            put("Project", 24);
        }};

        // when
        String actualJsonResponse = jsonResultPresentation.createResponse(unFilteredCountedWords);

        // then
        Assert.assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    public void testCreateResponse_mapWithNullValues() throws Exception {
        // given
        final String expectedJsonResponse = "{\"success\":true,\"unFilteredWords\":[[null,\"null\"]]}";

        final Map<String, Integer> unFilteredCountedWords = new HashMap<String, Integer>() {{
            put(null, null);
        }};

        // when
        String actualJsonResponse = jsonResultPresentation.createResponse(unFilteredCountedWords);

        // then
        Assert.assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateResponse_nullMap() {
        // given
        final Map<String, Integer> unFilteredCountedWords = null;

        // when
        jsonResultPresentation.createResponse(unFilteredCountedWords);

        // then
        // exception expected
    }

    @Test
    public void testCreateErrorResponse_validError() {
        // given
        String expectedJsonResponse = "{\"respMessage\":\"Results collection should not be null\"}";

        // when
        Throwable e = new IllegalArgumentException("Results collection should not be null");
        String actualJsonResponse = jsonResultPresentation.createErrorResponse(e);

        // then
        Assert.assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateErrorResponse_nullThrowable() throws Exception {
        // given
        Throwable e = null;

        // when
        jsonResultPresentation.createErrorResponse(e);

        // then
        // Exception expected
    }
}
