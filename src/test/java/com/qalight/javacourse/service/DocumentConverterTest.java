package com.qalight.javacourse.service;

import com.qalight.javacourse.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DocumentConverterTest {
    private DocumentConverter converter;

    @Before
    public void setup() {
        converter = new DocumentConverter();
    }

    @Test
    public void testGetDocumentConverter_pdf() {
        //given
        final TextType type = new PdfTextTypeImpl();

        //when
        DocumentToStringConverter toStringConverter = converter.getDocumentConverter(type);

        //then
        Assert.assertTrue(toStringConverter instanceof PdfToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_doc() {
        //given
        final TextType type = new DocTextTypeImpl();

        //when
        DocumentToStringConverter toStringConverter = converter.getDocumentConverter(type);

        //then
        Assert.assertTrue(toStringConverter instanceof DocToStringConverter);
    }

    @Test
    public void testGetDocumentConverter_html() {
        //given
        final TextType type = new HtmlTextTypeImpl();

        //when
        DocumentToStringConverter toStringConverter = converter.getDocumentConverter(type);

        //then
        Assert.assertTrue(toStringConverter instanceof HtmlToStringConverter);
    }

    @Test(expected = RuntimeException.class)
    public void testNonExistingType(){
        // given
        final TextType NON_EXISTING_TYPE = dataSourceLink -> true;

        //when
        converter.getDocumentConverter(NON_EXISTING_TYPE);
    }

}