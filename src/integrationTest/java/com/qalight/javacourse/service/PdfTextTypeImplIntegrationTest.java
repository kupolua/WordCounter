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
    public void testIsEligible_okUrl() {
        // given
        final String dataSourceLink = "http://defas.com.ua/java/Policy_of_.UA.pdf";
        final boolean expectedResult = true;

        //when
        final boolean actualResult = pdfTextType.isEligible(dataSourceLink);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIsEligible_improperUrl() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/testingPage.html";
        final boolean expectedResult = false;

        //when
        final boolean actualResult = pdfTextType.isEligible(dataSourceLink);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyUrl() {
        //given
        final String dataSourceLink = "";

        //when
        pdfTextType.isEligible(dataSourceLink);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullUrl() {
        //given
        final String dataSourceLink = null;

        // when
        pdfTextType.isEligible(dataSourceLink);

        //then
        //expected exception
    }

}