package service;

import com.qalight.javacourse.service.DocTextTypeImpl;
import com.qalight.javacourse.service.PdfTextTypeImpl;
import com.qalight.javacourse.service.PdfToStringConverter;
import com.qalight.javacourse.service.TextType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PdfToStringConverterTest {

    private PdfToStringConverter pdfToStringConverter;

    @Before
    public void setUp() {
        pdfToStringConverter = new PdfToStringConverter();
    }

    @Test
     public void testIsEligible()  {
        //given
        final TextType DOCUMENT_TYPE = new PdfTextTypeImpl();

        //when
        boolean actualResult = pdfToStringConverter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible()  {
        //given
        final TextType DOCUMENT_TYPE = new DocTextTypeImpl();

        //when
        boolean actualResult = pdfToStringConverter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertFalse(actualResult);
    }
}