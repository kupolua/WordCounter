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
}