package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

public class HtmlTextTypeImplTest {

    @Test
    public void testIsEligible() {
        HtmlTextTypeImpl typeIml = new HtmlTextTypeImpl();
        //given
        String sourceLink = "index.html";
        String sourceLink1 = "index";
        String sourceLink2 = "index.pdf";
        String sourceLink3 = "index.xml";
        //when
        boolean isEligible = typeIml.isEligible(sourceLink);
        boolean isEligible1 = typeIml.isEligible(sourceLink1);
        boolean isEligible2 = typeIml.isEligible(sourceLink2);
        boolean isEligible3 = typeIml.isEligible(sourceLink3);
        //then
        Assert.assertTrue(isEligible);
        Assert.assertTrue(isEligible1);
        Assert.assertFalse(isEligible2);
        Assert.assertFalse(isEligible3);
    }
}