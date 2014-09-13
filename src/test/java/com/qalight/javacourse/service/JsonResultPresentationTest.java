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
        final String expectedJsonResponse = "{\"success\":true,\"dataAjax\":[[\"Project\",\"24\"],[\"Word\",\"13\"],[\"Counter\",\"5\"],[\"Hello\",\"10\"],[\"World\",\"7\"]]}";
        final String sourceLink = "http://www.eslfast.com/supereasy/se/supereasy006.htm";
        final Map<String, Integer> countedWords = new HashMap<>();
        countedWords.put("Hello", 10);
        countedWords.put("World", 7);
        countedWords.put("Word", 13);
        countedWords.put("Counter", 5);
        countedWords.put("Project", 24);
        final String dataTypeResponse = "json";

        //when
        String actualJsonResponse = jsonResultPresentation.createResponse(sourceLink, countedWords, dataTypeResponse);

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