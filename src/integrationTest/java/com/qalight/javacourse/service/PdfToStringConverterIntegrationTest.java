package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class PdfToStringConverterIntegrationTest {

    @Autowired
    private PdfToStringConverter pdfToStringConverter;

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
        String actualResult = pdfToStringConverter.convertToString(URL);

        //then
        //expected exception
    }

    @Test(expected = NullPointerException.class)
    public void testConvertToString_null() {
        //given
        final String URL = null;

        //when
        String actualResult = pdfToStringConverter.convertToString(URL);

        //then
        //expected exception
    }
}