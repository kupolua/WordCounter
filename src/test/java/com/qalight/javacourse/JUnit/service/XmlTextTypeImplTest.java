package com.qalight.javacourse.JUnit.service;

import com.qalight.javacourse.service.XmlTextTypeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class XmlTextTypeImplTest {

    private XmlTextTypeImpl typeIml;

    @Before
    public void setup(){
        typeIml = new XmlTextTypeImpl();
    }

    @Test
    public void testIsEligible_woExtension() {
        //given
        final String sourceLink1 = "index";
        //when
        boolean isEligible1 = typeIml.isEligible(sourceLink1);
        //then
        Assert.assertFalse(isEligible1);
    }

    @Test
    public void testIsEligible_pdfExtension() {
        //given
        final String sourceLink2 = "index.pdf";
        //when
        boolean isEligible2 = typeIml.isEligible(sourceLink2);
        //then
        Assert.assertFalse(isEligible2);
    }

    @Test
    public void testIsEligible_xmlExtension() {
        //given
        final String sourceLink3 = "index.xml";
        //when
        boolean isEligible3 = typeIml.isEligible(sourceLink3);
        //then
        Assert.assertTrue(isEligible3);
    }

    @Test
    public void testIsEligible_htmlExtension() {
        //given
        final String sourceLink = "index.html";
        //when
        boolean isEligible = typeIml.isEligible(sourceLink);
        //then
        Assert.assertFalse(isEligible);
    }
}