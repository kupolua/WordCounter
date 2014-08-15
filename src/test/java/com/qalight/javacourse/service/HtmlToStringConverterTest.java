package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HtmlToStringConverterTest {

    private HtmlToStringConverter converter;

    @Before
    public void setup(){
        converter = new HtmlToStringConverter();
    }

    @Test
    public void testIsEligible_xml() {
        //given
        final String DOCUMENT_TYPE = "xml";
        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);
        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_html(){
        //given
        final String DOCUMENT_TYPE = "html";
        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);
        //then
        Assert.assertTrue(actualResult);
    }
}