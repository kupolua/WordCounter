package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PdfToStringConverterIntegrationTest {

    private PdfToStringConverter pdfToStringConverter;

    @Before
    public void setUp() {
        pdfToStringConverter = new PdfToStringConverter();
    }

    @Test
    public void testConvertToString() {
        //given
        final String URL = "http://defas.com.ua/java/Policy_of_.UA.pdf";

        //when
        String actualResult = pdfToStringConverter.convertToString(URL);

        //then
        Assert.assertNotNull(actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_badUrl() {
        //given
        final String URL = "http://www.xmlfiles.com/examples/cd_ca22123talog.xml";

        //when
        pdfToStringConverter.convertToString(URL);

        //then
        //expected exception
    }
}