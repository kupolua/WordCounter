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
    public void testIsEligible_validTextType() {
        // given
        final TextType documentType = new PlainTextTypeImpl();

        // when
        boolean actualResult = plainToStringConverter.isEligible(documentType);

        // then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidTextType() {
        // given
        final TextType documentType = new PdfTextTypeImpl();

        // when
        boolean actualResult = plainToStringConverter.isEligible(documentType);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_nullTextType() {
        // given
        final TextType documentType = null;

        // when
        boolean actualResult = plainToStringConverter.isEligible(documentType);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testConvertToString_valid() {
        // given
        final String inputText = "В мире есть много интересных занятий.";

        // when
        String actualResult = plainToStringConverter.convertToString(inputText);

        // then
        Assert.assertEquals(inputText, actualResult);
    }

    @Test
    public void testConvertToString_empty() {
        // given
        final String inputText = " ";

        // when
        String actualResult = plainToStringConverter.convertToString(inputText);

        // then
        Assert.assertEquals(inputText, actualResult);
    }

    @Test
    public void testConvertToString_null() {
        // given
        final String inputText = null;

        // when
        String actualResult = plainToStringConverter.convertToString(inputText);

        // then
        Assert.assertEquals(inputText, actualResult);
    }
}