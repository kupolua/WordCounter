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
    public void testGetHttpHeader_pptx() {
        //given
        final String dataSourceLink = "https://sctcc.ims.mnscu.edu/shared/CheckYourComputer/SamplePPTX.pptx";
        final String expectedTextHeader = "[application/vnd.openxmlformats-officedocument.presentationml.presentation]";

        //when
        final String actualResult = responseHeaderGetter.getHttpHeader(dataSourceLink);

        //then
        Assert.assertEquals(expectedTextHeader, actualResult);
    }

    @Test
    public void testGetHttpHeader_html() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/%D0%A1%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%87%D0%BA%D0%B0%20%D1%80%D1%83%D1%81.html";
        final String expectedTextHeader = "[text/html]";

        //when
        final String actualResult = responseHeaderGetter.getHttpHeader(dataSourceLink);

        //then
        Assert.assertEquals(expectedTextHeader, actualResult);
    }

    @Test
    public void testGetHttpHeader_plainText() {
        //given
        final String dataSourceLink = "This text contain http word, but not contain http link.";
        final String expectedTextHeader = "plain_text_type";

        //when
        final String actualResult = responseHeaderGetter.getHttpHeader(dataSourceLink);

        //then
        Assert.assertEquals(expectedTextHeader, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHttpHeader_emptyUrl() {
        //given
        final String dataSourceLink = "";

        //when
        responseHeaderGetter.getHttpHeader(dataSourceLink);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHttpHeader_NullUrl() {
        //given
        final String dataSourceLink = null;

        //when
        responseHeaderGetter.getHttpHeader(dataSourceLink);

        //then
        //expected exception
    }
}