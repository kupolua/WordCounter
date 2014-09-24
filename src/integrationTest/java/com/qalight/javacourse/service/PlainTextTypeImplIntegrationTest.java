package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlainTextTypeImplIntegrationTest {
    private PlainTextTypeImpl plainTextType;

    @Before
    public void setUp() throws Exception {
        plainTextType = new PlainTextTypeImpl();
    }

    @Test
    public void testIsEligible() {
        //given
        final String textHttpHeader = "plain_text_type";

        //when
        boolean actualResult = plainTextType.isEligible(textHttpHeader);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() throws Exception {
        //given
        final String textHttpHeader = "[text/html]";

        //when
        boolean actualResult = plainTextType.isEligible(textHttpHeader);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullLink() {
        //given
        final String textHttpHeader = null;

        //when
        plainTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyLink() {
        //given
        final String textHttpHeader = "";

        //when
        plainTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }
}