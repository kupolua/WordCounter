package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class XmlToStringConverterTest {

    private  XmlToStringConverter converter;

    @Before
    public void setup(){
        converter = new XmlToStringConverter();
    }

    @Test
    public void testIsEligible_xml() {
        //given
        final String DOCUMENT_TYPE = "xml";
        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);
        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_html(){
        //given
        final String DOCUMENT_TYPE = "html";
        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);
        //then
        Assert.assertFalse(actualResult);
    }
}