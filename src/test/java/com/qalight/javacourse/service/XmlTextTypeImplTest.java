package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;


public class XmlTextTypeImplTest {
    //todo diverfd: separate to some tests. Each public method have its test
    @Test
    public void testIsEligible() {
        XmlTextTypeImpl typeImpl = new XmlTextTypeImpl();
        //given
        final String sourceLink = "index.html";
        final String sourceLink1 = "index";
        final String sourceLink2 = "index.pdf";
        final String sourceLink3 = "index.xml";
        //when
        boolean isEligible = typeImpl.isEligible(sourceLink);
        boolean isEligible1 = typeImpl.isEligible(sourceLink1);
        boolean isEligible2 = typeImpl.isEligible(sourceLink2);
        boolean isEligible3 = typeImpl.isEligible(sourceLink3);
        //then
        Assert.assertFalse(isEligible);
        Assert.assertFalse(isEligible1);
        Assert.assertFalse(isEligible2);
        Assert.assertTrue(isEligible3);
    }
}