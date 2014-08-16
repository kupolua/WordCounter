package com.qalight.javacourse.Integration.service;

import com.qalight.javacourse.service.XmlToStringConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class XmlToStringConverterIT {

    private XmlToStringConverter converter;

    @Before
    public void setup() {
        converter = new XmlToStringConverter();
    }

    @Test
    public void testConvertToString() {
        //given
        final String xmlUrl = "http://www.w3schools.com/xml/cd_catalog.xml";
        //when
        String actualResult = converter.convertToString(xmlUrl);
        //then
        Assert.assertNotNull(actualResult);
    }
}