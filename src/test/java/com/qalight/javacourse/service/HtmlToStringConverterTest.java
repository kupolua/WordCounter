package com.qalight.javacourse.service;

import static org.mockito.Mockito.*;

import org.jsoup.examples.HtmlToPlainText;
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

    @Mock private Document document;
    @Mock private HtmlToPlainText htmlToPlainText;
    @Spy private HtmlToStringConverter spyConverter;
    private HtmlToStringConverter converter;

    @Before
    public void setup() {
        converter = new HtmlToStringConverter();
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType docTextType = new HtmlTextTypeImpl();

        //when
        boolean actualResult = converter.isEligible(docTextType);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {
        //given
        final TextType docTextType = new PdfTextTypeImpl();

        //when
        boolean actualResult = converter.isEligible(docTextType);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void convertToString() throws Exception{
        //given
        final String input = "http://html-examples.com/example.html";
        final String expected = "some text";
        doReturn(document).when(spyConverter).getDocument(anyString());
        doReturn(htmlToPlainText).when(spyConverter).getHtmlToPlainText();
        when(htmlToPlainText.getPlainText(any(Document.class))).thenReturn(expected);

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, times(1)).getHtmlToPlainText();
        verify(spyConverter, times(1)).getDocument(anyString());
        verify(htmlToPlainText, times(1)).getPlainText(any(Document.class));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void convertToString_throwingException() throws Exception{
        //given
        final String input = "http://html-examples.com/example.html";
        final String expected = "some text";
        doThrow(new IOException("test")).when(spyConverter).getDocument(anyString());
        doReturn(htmlToPlainText).when(spyConverter).getHtmlToPlainText();
        when(htmlToPlainText.getPlainText(any(Document.class))).thenReturn(expected);

        //when
        final String actual = spyConverter.convertToString(input);

        //then
        verify(spyConverter, times(1)).getHtmlToPlainText();
        verify(spyConverter, times(1)).getDocument(anyString());
        verify(htmlToPlainText, never()).getPlainText(any(Document.class));
        // exception expected
    }
}