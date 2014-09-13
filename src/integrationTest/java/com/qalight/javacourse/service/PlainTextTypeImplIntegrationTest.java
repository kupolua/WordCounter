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
    public void testIsEligible() throws Exception {
        //given
        final String PLAIN_TEXT = "В мире есть много интересных занятий.";

        //when
        boolean actualResult = plainTextType.isEligible(PLAIN_TEXT);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() throws Exception {
        //given
        final String DATA_SOURCE_LINK = "http://defas.com.ua/java/Policy_of_.UA.pdf";

        //when
        boolean actualResult = plainTextType.isEligible(DATA_SOURCE_LINK);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullLink() {
        //given
        final String NULL_LINK = null;

        //when
        plainTextType.isEligible(NULL_LINK);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyLink() {
        //given
        final String EMPTY_LINK = "";

        //when
        plainTextType.isEligible(EMPTY_LINK);

        //then
        //expected exception
    }
}