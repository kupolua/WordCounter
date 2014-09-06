package service;

import com.qalight.javacourse.service.HtmlTextTypeImpl;
import com.qalight.javacourse.service.HtmlToStringConverter;
import com.qalight.javacourse.service.PdfTextTypeImpl;
import com.qalight.javacourse.service.TextType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HtmlToStringConverterTest {

    private HtmlToStringConverter converter;

    @Before
    public void setup() {
        converter = new HtmlToStringConverter();
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType DOCUMENT_TYPE = new HtmlTextTypeImpl();

        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {

        //given
        final TextType DOCUMENT_TYPE = new PdfTextTypeImpl();

        //when
        boolean actualResult = converter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertFalse(actualResult);
    }
}