package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

public class TextTypeInquirerTest {

    @Test
    public void testInquireTextType_woExtension() {
        TextTypeInquirer inquirer = new TextTypeInquirer();
        //given
        final String TEXT_LINK1 = "index";
        //when
        TextType actualTextType1 = inquirer.inquireTextType(TEXT_LINK1);
        //then
        Assert.assertTrue(actualTextType1 instanceof HtmlTextTypeImpl);
    }

    @Test
    public void testInquireTextType_xmlExtension() {
        TextTypeInquirer inquirer = new TextTypeInquirer();
        //given
        final String TEXT_LINK2 = "index.xml";
        //when
        TextType actualTextType2 = inquirer.inquireTextType(TEXT_LINK2);
        //then
        Assert.assertTrue(actualTextType2 instanceof XmlTextTypeImpl);
    }

    @Test
    public void testInquireTextType_htmlExtension() {
        TextTypeInquirer inquirer = new TextTypeInquirer();
        //given
        final String TEXT_LINK = "index.html";
        //when
        TextType actualTextType = inquirer.inquireTextType(TEXT_LINK);
        //then
        Assert.assertTrue(actualTextType instanceof HtmlTextTypeImpl);
    }
}