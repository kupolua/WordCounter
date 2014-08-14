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
        String actualResult = converter.convertToString(xmlUrl);
        //then
        Assert.assertNotNull(actualResult);
    }
}