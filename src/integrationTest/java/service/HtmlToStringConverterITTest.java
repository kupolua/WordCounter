package service;

import com.qalight.javacourse.service.HtmlToStringConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HtmlToStringConverterITTest {

    private HtmlToStringConverter converter;

    @Before
    public void setup() {
        converter = new HtmlToStringConverter();
    }

    @Test
    public void testConvertToString() {
        //given
        final String url = "http://bbc.com";
        //when
        String actualResult = converter.convertToString(url);
        //then
        Assert.assertNotNull(actualResult);
    }
}