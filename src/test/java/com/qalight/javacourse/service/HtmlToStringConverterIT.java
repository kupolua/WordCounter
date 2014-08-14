package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;


public class HtmlToStringConverterIT {

    @Test
    public void testConvertToString() {
        HtmlToStringConverter converter = new HtmlToStringConverter();
        //given
        final String url = "http://bbc.com";
        //when
        String expectedResult = converter.convertToString(url);
        //then
        Assert.assertNotNull(expectedResult);
    }
}