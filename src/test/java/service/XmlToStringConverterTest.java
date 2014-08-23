package service;

import com.qalight.javacourse.service.HtmlTextTypeImpl;
import com.qalight.javacourse.service.TextType;
import com.qalight.javacourse.service.XmlTextTypeImpl;
import com.qalight.javacourse.service.XmlToStringConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class XmlToStringConverterTest {

    private XmlToStringConverter converter;

    @Before
    public void setup() {
        converter = new XmlToStringConverter();
    }

    @Test
    public void testIsEligible_xml() {
        //given
        final TextType DOCUMENT_TYPE = new XmlTextTypeImpl();

        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_html() {
        //given
        final TextType DOCUMENT_TYPE = new HtmlTextTypeImpl();

        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertFalse(actualResult);
    }

    // todo: test all methods. you can use mocking for JSoup
}