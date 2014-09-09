package com.qalight.javacourse.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DocToStringConverterIntegrationTest {

    private DocToStringConverter docToStringConverter;

    @Before
    public void setUp() {
        docToStringConverter = new DocToStringConverter();
    }

    @Test
    public void testConvertToString() {
        //given
        final String URL = "http://www.snee.com/xml/xslt/sample.doc";

        //when
        String actualResult = docToStringConverter.convertToString(URL);

        //then
        assertNotNull(actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_badUrl() {
        //given
        final String URL = "http://www.xmlfiles.com/examples/cd_ca22123talog";

        //when
        docToStringConverter.convertToString(URL);

        //then
        //expected exception
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_woProtocol() {
        //given
        final String URL = "www.xmlfiles.com/examples/cd_ca22123talog";

        //when
        docToStringConverter.convertToString(URL);

        //then
        //expected exception
    }
}