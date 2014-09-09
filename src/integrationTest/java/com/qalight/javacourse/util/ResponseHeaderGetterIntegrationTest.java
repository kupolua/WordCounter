package com.qalight.javacourse.util;

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
    public void testGetTextTypeByHttpHeader_plainText() {
        //given
        final String dataSourceLink = "Путин Ху*ло ла, ла, ла!!!";
        final String expectedResult = "plain_text_type";

        //when
        final String actualResult = responseHeaderGetter.getTextTypeByHttpHeader(dataSourceLink);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTextTypeByHttpHeader_emptyUrl() {
        //given
        final String dataSourceLink = "";

        //when
        responseHeaderGetter.getTextTypeByHttpHeader(dataSourceLink);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTextTypeByHttpHeader_NullUrl() {
        //given
        final String dataSourceLink = null;

        //when
        responseHeaderGetter.getTextTypeByHttpHeader(dataSourceLink);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTextTypeByHttpHeader_exeType() {
        //given
        final String URL = "www.xmlfiles.com/examples/cd_ca22123talog";

        //when
        responseHeaderGetter.getTextTypeByHttpHeader(URL);

        //then
        //expected exception
    }
}