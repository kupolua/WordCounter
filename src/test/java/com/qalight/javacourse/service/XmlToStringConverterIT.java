package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;


public class XmlToStringConverterIT {

    @Test
    public void testConvertToString() {
        XmlToStringConverter converter = new XmlToStringConverter();
        //given
        final String xmlUrl = "http://www.w3schools.com/xml/cd_catalog.xml";
        //when
        String expectedResult = converter.convertToString(xmlUrl);
        //then
        Assert.assertNotNull(expectedResult);
    }
}