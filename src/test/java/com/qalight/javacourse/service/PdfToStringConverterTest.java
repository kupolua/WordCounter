package com.qalight.javacourse.service;

import static org.mockito.Mockito.*;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.StringJoiner;

@RunWith(MockitoJUnitRunner.class)
public class PdfToStringConverterTest {
    @Mock private PdfReader mockedReader;
    @Mock private PdfTextExtractor extractor;
    @Spy PdfToStringConverter spyConverter;
    private PdfToStringConverter pdfToStringConverter;

    @Before
    public void setUp() {
        pdfToStringConverter = new PdfToStringConverter();
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType docTextType = new PdfTextTypeImpl();

        //when
        boolean actualResult = pdfToStringConverter.isEligible(docTextType);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {
        //given
        final TextType docTextType = new DocTextTypeImpl();

        //when
        boolean actualResult = pdfToStringConverter.isEligible(docTextType);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testConvertToString() throws Exception {
        //given
        final String input = "http://pdf-examples.com/example.pdf";
        final String expected = "example text";

        doReturn(mockedReader).when(spyConverter).getPdfReader(anyString());
        doReturn(expected).when(spyConverter).getTextFromAllPages(any(PdfReader.class));

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, times(1)).getPdfReader(anyString());
        verify(spyConverter, times(1)).getTextFromAllPages(any(PdfReader.class));

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testConvertToString_throwingException() throws Exception {
        //given
        final String input = "pdf-examples.com/example.pdf";
        final String expected = "example text";

        doThrow(new IOException("test")).when(spyConverter).getPdfReader(anyString());
        doReturn(expected).when(spyConverter).getTextFromAllPages(any(PdfReader.class));

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, times(1)).getPdfReader(anyString());
        verify(spyConverter, never()).getTextFromAllPages(any(PdfReader.class));
        // exception expected
    }

    @Test
    public void testGetTextFromAllPages() throws Exception {
        //given
        final String expected = "example text";
        final int numberOfPages = 1;

        when(mockedReader.getNumberOfPages()).thenReturn(numberOfPages);
        when(extractor.getTextFromPage(numberOfPages)).thenReturn(expected);
        doReturn(extractor).when(spyConverter).getPdfTextExtractor(any(PdfReader.class));
        doReturn(expected).when(spyConverter).getString(any(StringJoiner.class));

        //when
        final String actual = spyConverter.getTextFromAllPages(mockedReader);

        //then
        verify(mockedReader, atLeastOnce()).getNumberOfPages();
        verify(extractor, times(1)).getTextFromPage(numberOfPages);
        verify(spyConverter, times(1)).getString(any(StringJoiner.class));
        verify(spyConverter, times(1)).getPdfTextExtractor(any(PdfReader.class));

        Assert.assertEquals(expected, actual);
    }
}