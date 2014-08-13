package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

public class DocumentConverterTest {

    @Test
    public void testGetDocumentConverter() {
        DocumentConverter converter = new DocumentConverter();
        //given
        final String type = "xml";
        final String type1 = "html";
        //when
        DocumentToStringConverter toStringConverter = converter.getDocumentConverter(type);
        DocumentToStringConverter toStringConverter1 = converter.getDocumentConverter(type1);
        //then
        Assert.assertTrue(toStringConverter instanceof XmlToStringConverter);
        Assert.assertTrue(toStringConverter1 instanceof HtmlToStringConverter);
    }
}