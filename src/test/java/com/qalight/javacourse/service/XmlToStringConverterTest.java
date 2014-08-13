package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;


public class XmlToStringConverterTest {

    @Test
    public void testIsEligible_xml() {
        XmlToStringConverter converter = new XmlToStringConverter();
        //given
        final String DOCUMENT_TYPE = "xml";
        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);
        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_html(){
        XmlToStringConverter converter = new XmlToStringConverter();
        //given
        final String DOCUMENT_TYPE = "html";
        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);
        //then
        Assert.assertFalse(actualResult);
    }
}