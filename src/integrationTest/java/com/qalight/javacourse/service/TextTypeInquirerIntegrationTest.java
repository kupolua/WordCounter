package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextTypeInquirerIntegrationTest {
    private TextTypeInquirer textTypeInquirer;

    @Before
    public void setup() {
        textTypeInquirer = new TextTypeInquirer();
    }

    @Test
    public void testInquireTextType_html() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/testingPage.html";
        final String expectedResult = "com.qalight.javacourse.service.HtmlTextTypeImpl@";

        //when
        final TextType actualResult = textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        Assert.assertTrue(actualResult.toString().startsWith(expectedResult));
    }

    @Test
    public void testInquireTextType_doc() {
        //given
        final String dataSourceLink = "http://www.snee.com/xml/xslt/sample.doc";

        //when
        final TextType actualResult = textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        Assert.assertTrue(actualResult instanceof DocTextTypeImpl);
    }

    @Test
    public void testInquireTextType_pdf() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/Policy_of_.UA.pdf";
        final String expectedResult = "com.qalight.javacourse.service.PdfTextTypeImpl@";

        //when
        final TextType actualResult = textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        Assert.assertTrue(actualResult.toString().startsWith(expectedResult));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInquireTextType_unsupportedFormat() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/fdbasecd.iso";

        //when
        textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInquireTextType_emptyUrl() {
        //given
        final String dataSourceLink = "";

        //when
        textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInquireTextType_NullUrl() {
        //given
        final String dataSourceLink = null;

        textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        //expected exception
    }

}