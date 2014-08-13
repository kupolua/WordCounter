package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;


public class HtmlToStringConverterTest {

    @Test
    public void testIsEligible_xml() {
        HtmlToStringConverter converter = new HtmlToStringConverter();
        //given
        final String DOCUMENT_TYPE = "xml";
        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);
        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_html(){
        HtmlToStringConverter converter = new HtmlToStringConverter();
        //given
        final String DOCUMENT_TYPE = "html";
        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);
        //then
        Assert.assertTrue(actualResult);
    }
}