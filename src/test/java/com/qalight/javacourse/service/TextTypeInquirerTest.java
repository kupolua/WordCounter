package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextTypeInquirerTest {

    private TextTypeInquirer inquirer;

    @Before
    public void setup(){
        inquirer = new TextTypeInquirer();
    }

    @Test
    public void testInquireTextType_woExtension() {
        //given
        final String TEXT_LINK1 = "index";
        //when
        TextType actualTextType1 = inquirer.inquireTextType(TEXT_LINK1);
        //then
        Assert.assertTrue(actualTextType1 instanceof HtmlTextTypeImpl);
    }

    @Test
    public void testInquireTextType_xmlExtension() {
        //given
        final String TEXT_LINK2 = "index.xml";
        //when
        TextType actualTextType2 = inquirer.inquireTextType(TEXT_LINK2);
        //then
        Assert.assertTrue(actualTextType2 instanceof XmlTextTypeImpl);
    }

    @Test
    public void testInquireTextType_htmlExtension() {
        //given
        final String TEXT_LINK = "index.html";
        //when
        TextType actualTextType = inquirer.inquireTextType(TEXT_LINK);
        //then
        Assert.assertTrue(actualTextType instanceof HtmlTextTypeImpl);
    }
}