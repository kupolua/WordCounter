package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

public class HtmlTextTypeImplTest {
    //todo diverfd: separate to some tests. Each public method have its test
    @Test
    public void testIsEligible() {
        HtmlTextTypeImpl typeIml = new HtmlTextTypeImpl();
        //given
        final String sourceLink = "index.html";
        final String sourceLink1 = "index";
        final String sourceLink2 = "index.pdf";
        final String sourceLink3 = "index.xml";
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