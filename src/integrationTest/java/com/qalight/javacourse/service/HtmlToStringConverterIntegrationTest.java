package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class HtmlToStringConverterIntegrationTest {

    @Autowired
    private HtmlToStringConverter converter;

    @Test
    public void testConvertToString_html() {
        //given
        final String URL = "http://bbc.com";

        //when
        String actualResult = converter.convertToString(URL);

        //then
        Assert.assertNotNull(actualResult);
    }

    @Test
    public void testConvertToString_https() {
        //given
        final String URL = "https://www.textnow.com";

        //when
        String actualResult = converter.convertToString(URL);

        //then
        Assert.assertNotNull(actualResult);
    }

    @Test
    public void testConvertToString_xml() {
        //given
        final String URL = "http://www.xmlfiles.com/examples/cd_catalog.xml";

        //when
        String actualResult = converter.convertToString(URL);

        //then
        Assert.assertNotNull(actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_badUrl() {
        //given
        final String URL = "http://www.xmlfiles.com/examples/cd_ca22123talog.xml";

        //when
        converter.convertToString(URL);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToString_null() {
        //given
        final String URL = null;

        //when
        converter.convertToString(URL);

        //then
        //expected exception
    }
}