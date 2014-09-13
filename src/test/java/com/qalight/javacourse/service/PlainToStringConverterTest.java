package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlainToStringConverterTest {

    private PlainToStringConverter plainToStringConverter;

    @Before
    public void setUp() throws Exception {
        plainToStringConverter = new PlainToStringConverter();
    }

    @Test
    public void testIsEligible() throws Exception {
        //given
        final TextType DOCUMENT_TYPE = new PlainTextTypeImpl();

        //when
        boolean actualResult = plainToStringConverter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() throws Exception {
        //given
        final TextType DOCUMENT_TYPE = new PdfTextTypeImpl();

        //when
        boolean actualResult = plainToStringConverter.isEligible(DOCUMENT_TYPE);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testConvertToString() throws Exception {
        //given
        final String PLAIN_TEXT = "В мире есть много интересных занятий.";

        //when
        String actualResult = plainToStringConverter.convertToString(PLAIN_TEXT);

        //then
        Assert.assertEquals("string should be equal", PLAIN_TEXT, actualResult);
    }
}