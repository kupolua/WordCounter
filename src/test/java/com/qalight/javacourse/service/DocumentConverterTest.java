package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

public class DocumentConverterTest {

    @Test
    public void testGetDocumentConverter_xml() {
        DocumentConverter converter = new DocumentConverter();
        //given
        final String type = "xml";
        //when
        DocumentToStringConverter toStringConverter = converter.getDocumentConverter(type);
        //then
        Assert.assertTrue(toStringConverter instanceof XmlToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_html() {
        DocumentConverter converter = new DocumentConverter();
        //given
        final String type1 = "html";
        //when
        DocumentToStringConverter toStringConverter1 = converter.getDocumentConverter(type1);
        //then
        Assert.assertTrue(toStringConverter1 instanceof HtmlToStringConverter);
    }
}