package com.qalight.javacourse.service;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PdfToStringConverterTest {

    private PdfToStringConverter pdfToStringConverter;

    @Before
    public void setUp() {
        pdfToStringConverter = new PdfToStringConverter();
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType DOCUMENT_TYPE = new PdfTextTypeImpl();

        //when
        boolean actualResult = pdfToStringConverter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {
        //given
        final TextType DOCUMENT_TYPE = new DocTextTypeImpl();

        //when
        boolean actualResult = pdfToStringConverter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testConvertToString() {
        //given
        final String input = "http://pdf-examples.com/example.pdf";

        PdfToStringConverter mockedConverter = mock(PdfToStringConverter.class);
        when(mockedConverter.convertToString(input)).thenReturn("example text");

        //when
        final String actual = mockedConverter.convertToString(input);

        //then
        verify(mockedConverter, times(1)).convertToString(any(String.class));
        Assert.assertEquals("example text", actual);
    }
}