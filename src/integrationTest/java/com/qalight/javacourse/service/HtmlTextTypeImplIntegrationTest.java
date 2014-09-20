package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HtmlTextTypeImplIntegrationTest {
    HtmlTextTypeImpl htmlTextType;

    @Before
    public void setup() {
        htmlTextType = new HtmlTextTypeImpl();
    }

    @Test
    public void testIsEligible_html() {
        //given
        final String textHttpHeader = "[text/html; charset=windows-1251]";

        //when
        final boolean actualResult = htmlTextType.isEligible(textHttpHeader);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_xml() {
        //given
        final String textHttpHeader = "[text/xml]";

        //when
        final boolean actualResult = htmlTextType.isEligible(textHttpHeader);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_improperType() {
        //given
        final String textHttpHeader = "[application/pdf]";
        final boolean expectedResult = false;

        //when
        final boolean actualResult = htmlTextType.isEligible(textHttpHeader);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyUrl() {
        //given
        final String textHttpHeader = "";

        //when
        htmlTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullUrl() {
        //given
        final String textHttpHeader = null;

        //when
        htmlTextType.isEligible(textHttpHeader);

        //then expected exception
    }

}