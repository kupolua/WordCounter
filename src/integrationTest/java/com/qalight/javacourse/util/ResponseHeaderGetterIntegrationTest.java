package com.qalight.javacourse.util;

import com.qalight.javacourse.util.ResponseHeaderGetter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResponseHeaderGetterIntegrationTest {

    private ResponseHeaderGetter responseHeaderGetter;

    @Before
    public void setup() {
        responseHeaderGetter = new ResponseHeaderGetter();
    }

    @Test
    public void testGetTextTypeByHttpHeader_html() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/testingPage.html";
        final String expectedResult = "[text/html]";

        //when
        final String actualResult = responseHeaderGetter.getTextTypeByHttpHeader(dataSourceLink);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetTextTypeByHttpHeader_emptyUrl() {
        //given
        final String dataSourceLink = "";
        final String expectedExceptoin = "java.lang.IllegalArgumentException: " +
                "It is impossible to determine the type of the document because the link is empty.";

        //when
        Exception actualException = null;
        try {
            responseHeaderGetter.getTextTypeByHttpHeader(dataSourceLink);
        } catch (IllegalArgumentException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedExceptoin, actualException.toString());
    }

    @Test
    public void testGetTextTypeByHttpHeader_NullUrl() {
        //given
        final String dataSourceLink = null;
        final String expectedExceptoin = "java.lang.NullPointerException: " +
                "It is impossible to determine the type of the document because the link is null.";

        //when
        Exception actualException = null;
        try {
            responseHeaderGetter.getTextTypeByHttpHeader(dataSourceLink);
        } catch (NullPointerException e) {
            actualException = e;
        }

        //then
        Assert.assertEquals(expectedExceptoin, actualException.toString());
    }

}