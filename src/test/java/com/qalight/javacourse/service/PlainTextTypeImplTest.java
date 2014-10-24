package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlainTextTypeImplTest {

    private PlainTextTypeImpl plainTextType;

    @Before
    public void setUp() throws Exception {
        plainTextType = new PlainTextTypeImpl();
    }

    @Test
    public void testIsEligible_plainText() {
        //given
        String plainText = "some plain text";

        //when
        boolean actualResult = plainTextType.isEligible(plainText);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_validHttpUrl() {
        //given
        String httpLink = "http://defas.com.ua/java/testingPage.html";

        //when
        boolean actualResult = plainTextType.isEligible(httpLink);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_validHttpsUrl() {
        //given
        String httpsLink = "https://mail.google.com";

        //when
        boolean actualResult = plainTextType.isEligible(httpsLink);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        //given
        final String url = " ";

        //when
        plainTextType.isEligible(url);

        //then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        //given
        final String url = null;

        //when
        plainTextType.isEligible(url);

        //then
        // exception thrown
    }
}