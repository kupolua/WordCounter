package com.qalight.javacourse.service;

import com.qalight.javacourse.util.ResponseHeaderGetter;
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
    public void testInquireTextType_plainText() {
        //given
        final String textHttpHeader = "plain_text_type";

        //when
        final ResponseHeaderGetter responseHeaderGetter = new ResponseHeaderGetter();
        final String textType = responseHeaderGetter.getHttpHeader(textHttpHeader);
        final TextType actualTextType = textTypeInquirer.inquireTextType(textType);

        //then
        Assert.assertTrue(actualTextType instanceof PlainTextTypeImpl);
    }

    @Test
    public void testInquireTextType_html() {
        //given
        final String dataSourceLink = "http://defas.com.ua/java/testingPage.html";

        //when
        final TextType actualTextType = textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        Assert.assertTrue(actualTextType instanceof HtmlTextTypeImpl);
    }

    @Test
    public void testInquireTextType_doc() {
        //given
        final String dataSourceLink = "http://www.snee.com/xml/xslt/sample.doc";

        //when
        final TextType actualTextType = textTypeInquirer.inquireTextType(dataSourceLink);

        //then
        Assert.assertTrue(actualTextType instanceof DocTextTypeImpl);
    }

    @Test
    public void testInquireTextType_pdf() {
        //given
        final String textHttpHeader = "http://defas.com.ua/java/Policy_of_.UA.pdf";

        //when
        final TextType actualTextType = textTypeInquirer.inquireTextType(textHttpHeader);

        //then
        Assert.assertTrue(actualTextType instanceof PdfTextTypeImpl);
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