package com.qalight.javacourse.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class DocToStringConverterIntegrationTest {

    @Autowired
    private DocToStringConverter docToStringConverter;

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