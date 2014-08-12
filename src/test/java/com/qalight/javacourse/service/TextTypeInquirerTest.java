package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

public class TextTypeInquirerTest {

    @Test
    public void testInquireTextType() {
        TextTypeInquirer inquirer = new TextTypeInquirer();
        //given
        final String TEXT_LINK = "index.html";
        final String TEXT_LINK1 = "index";
        final String TEXT_LINK2 = "index.xml";
        //when
        TextType actualTextType = inquirer.inquireTextType(TEXT_LINK);
        TextType actualTextType1 = inquirer.inquireTextType(TEXT_LINK1);
        TextType actualTextType2 = inquirer.inquireTextType(TEXT_LINK2);
        //then
        Assert.assertTrue(actualTextType instanceof HtmlTextTypeImpl);
        Assert.assertTrue(actualTextType1 instanceof HtmlTextTypeImpl);
        Assert.assertTrue(actualTextType2 instanceof XmlTextTypeImpl);
    }
}