package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

public class HtmlTextTypeImplTest {
    @Test
    public void testIsEligible_woExtension() {
        HtmlTextTypeImpl typeIml = new HtmlTextTypeImpl();
        //given
        final String sourceLink1 = "index";
        //when
        boolean isEligible1 = typeIml.isEligible(sourceLink1);
        //then
        Assert.assertTrue(isEligible1);
    }

    @Test
    public void testIsEligible_pdfExtension() {
        HtmlTextTypeImpl typeIml = new HtmlTextTypeImpl();
        //given
        final String sourceLink2 = "index.pdf";
        //when
        boolean isEligible2 = typeIml.isEligible(sourceLink2);
        //then
        Assert.assertFalse(isEligible2);
    }

    @Test
    public void testIsEligible_xmlExtension() {
        HtmlTextTypeImpl typeIml = new HtmlTextTypeImpl();
        //given
        final String sourceLink3 = "index.xml";
        //when
        boolean isEligible3 = typeIml.isEligible(sourceLink3);
        //then
        Assert.assertFalse(isEligible3);
    }

    @Test
    public void testIsEligible_htmlExtension() {
        HtmlTextTypeImpl typeIml = new HtmlTextTypeImpl();
        //given
        final String sourceLink = "index.html";
        //when
        boolean isEligible = typeIml.isEligible(sourceLink);
        //then
        Assert.assertTrue(isEligible);
    }
}