package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PdfTextTypeImplIntegrationTest {
    private PdfTextTypeImpl pdfTextType;

    @Before
    public void setup() {
        pdfTextType = new PdfTextTypeImpl();
    }

    @Test
    public void testIsEligible_okTypeHeader() {
        // given
        final String textHttpHeader = "[application/pdf]";
        final boolean expectedResult = true;

        //when
        final boolean actualResult = pdfTextType.isEligible(textHttpHeader);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIsEligible_improperTypeHeader() {
        //given
        final String textHttpHeader = "[text/html; charset=windows-1251]";
        final boolean expectedResult = false;

        //when
        final boolean actualResult = pdfTextType.isEligible(textHttpHeader);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyHeader() {
        //given
        final String textHttpHeader = "";

        //when
        pdfTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullHeader() {
        //given
        final String textHttpHeader = null;

        // when
        pdfTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }

}