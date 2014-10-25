package com.qalight.javacourse.service;

import static org.mockito.Mockito.*;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class HtmlToStringConverterTest {
    private HtmlToStringConverter converter;

    @Mock
    private Document document;
    @Spy
    private HtmlToStringConverter spyConverter;

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

    @Test
    public void convertToString() throws Exception{
        //given
        final String input = "http://pdf-examples.com/example.html";
        final String expected = "some text";
        doReturn(document).when(spyConverter).getDocument(anyString());
        doReturn(expected).when(spyConverter).getPlainText(any(Document.class));

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, times(1)).getPlainText(any(Document.class));
        verify(spyConverter, times(1)).getDocument(anyString());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void convertToString_throwingException() throws Exception{
        //given
        final String input = "http://pdf-examples.com/example.html";
        final String expected = "some text";
        doThrow(new IOException("test")).when(spyConverter).getDocument(anyString());
        doReturn(expected).when(spyConverter).getPlainText(any(Document.class));

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, never()).getPlainText(any(Document.class));
        verify(spyConverter, times(1)).getDocument(anyString());
        Assert.assertEquals(expected, actual);
    }
}