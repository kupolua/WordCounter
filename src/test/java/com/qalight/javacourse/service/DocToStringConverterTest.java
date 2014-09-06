package com.qalight.javacourse.service;

import com.qalight.javacourse.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DocToStringConverterTest {

    private DocToStringConverter docToStringConverter;

    @Before
    public void setUp() throws Exception {
        docToStringConverter = new DocToStringConverter();
    }

    @Test
    public void testIsEligible() {
        //given
        final TextType DOCUMENT_TYPE = new DocTextTypeImpl();

        //when
        boolean actualResult = docToStringConverter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() {

        //given
        final TextType DOCUMENT_TYPE = new PdfTextTypeImpl();

        //when
        boolean actualResult = docToStringConverter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertFalse(actualResult);
    }
}