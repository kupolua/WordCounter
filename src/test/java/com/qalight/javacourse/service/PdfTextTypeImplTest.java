package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PdfTextTypeImplTest {
    PdfTextTypeImpl pdfTextType;

    @Before
    public void setUp() throws Exception {
        pdfTextType = new PdfTextTypeImpl();
    }

    @Test
    public void testIsEligible_validType() {
        // given
        final String validTypeUrl = "http:// defas.com.ua/java/textForTest.pdf";

        // when
        boolean actualResult = pdfTextType.isEligible(validTypeUrl);

        // then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidType() {
        // given
        final String invalidTypeUrl = "http:// defas.com.ua/java/textForTest.doc";

        // when
        boolean actualResult = pdfTextType.isEligible(invalidTypeUrl);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_invalidUrl() {
        // given
        final String invalidUrl = "defas.com.ua/java/textForTest.pdf";

        // when
        boolean actualResult = pdfTextType.isEligible(invalidUrl);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String url = " ";

        // when
        pdfTextType.isEligible(url);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        // given
        final String url = null;

        // when
        pdfTextType.isEligible(url);

        // then
        // exception thrown
    }
}