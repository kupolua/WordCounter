package com.qalight.javacourse.service;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class DocumentConverterTest {
    @Mock private HtmlTextTypeImpl htmlType;
    @Mock private PdfTextTypeImpl pdfType;
    @Mock private DocTextTypeImpl docType;
    @Mock private PlainTextTypeImpl plainType;
    @Mock private HtmlToStringConverter htmlConverter;
    @Mock private PdfToStringConverter pdfConverter;
    @Mock private DocToStringConverter docConverter;
    @Mock private PlainToStringConverter plainConverter;
    private DocumentConverter converter;
    private Set<DocumentToStringConverter> documentToStringConverters;

    @Before
    public void setup() {
        converter = new DocumentConverter();
        documentToStringConverters = new HashSet<>();
        documentToStringConverters.add(htmlConverter);
        documentToStringConverters.add(pdfConverter);
        documentToStringConverters.add(docConverter);
        documentToStringConverters.add(plainConverter);

        DocumentConverter.setDocumentToStringConverters(documentToStringConverters);
    }

    @Test
    public void testGetDocumentConverter_pdf() {
        //given
        final TextType type = pdfType;
        when(pdfConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(pdfConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof PdfToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_doc() {
        //given
        final TextType type = docType;
        when(docConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(docConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof DocToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_html() {
        //given
        final TextType type = htmlType;
        when(htmlConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(htmlConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof HtmlToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_plainText() {
        //given
        final TextType type = plainType;
        when(plainConverter.isEligible(type)).thenReturn(true);

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(type);

        //then
        verify(plainConverter, times(1)).isEligible(type);
        Assert.assertTrue(actual instanceof PlainToStringConverter);
    }

    @Test(expected = RuntimeException.class)
    public void testNonExistingType() {
        // given
        final TextType NON_EXISTING_TYPE = dataSourceLink -> true;

        //when
        final DocumentToStringConverter actual = converter.getDocumentConverter(NON_EXISTING_TYPE);

        //then
        //expected exception
    }
}