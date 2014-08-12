package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;


public class XmlTextTypeImplTest {

    @Test
    public void testIsEligible() {
        XmlTextTypeImpl typeImpl = new XmlTextTypeImpl();
        //given
        String sourceLink = "index.html";
        String sourceLink1 = "index";
        String sourceLink2 = "index.pdf";
        String sourceLink3 = "index.xml";
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