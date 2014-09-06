package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HtmlToStringConverterIntegrationTest {

    private HtmlToStringConverter converter;

    @Before
    public void setup() {
        converter = new HtmlToStringConverter();
    }

    @Test
    public void testConvertToString_html() {
        //given
        final String URL = "http://bbc.com";

        //when
        String actualResult = converter.convertToString(URL);

        //then
        Assert.assertNotNull(actualResult);
    }

    @Test
    public void testConvertToString_xml() {
        //given
        final String URL = "http://www.xmlfiles.com/examples/cd_catalog.xml";

        //when
        String actualResult = converter.convertToString(URL);

        //then
        Assert.assertNotNull(actualResult);
    }
}