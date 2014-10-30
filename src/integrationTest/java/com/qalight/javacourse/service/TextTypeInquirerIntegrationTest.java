package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class TextTypeInquirerIntegrationTest {

    @Autowired
    private TextTypeInquirer textTypeInquirer;

    @Test
    public void testInquireTextType_plainText() {
        //given
        final String input = "plain text type";

        //when
        final TextType actualTextType = textTypeInquirer.inquireTextType(input);

        //then
        Assert.assertTrue(actualTextType instanceof PlainTextTypeImpl);
    }

    @Test
    public void testInquireTextType_html() {
        //given
        final String input = "http://defas.com.ua/java/testingPage.html";

        //when
        final TextType actualTextType = textTypeInquirer.inquireTextType(input);

        //then
        Assert.assertTrue(actualTextType instanceof HtmlTextTypeImpl);
    }

    @Test
    public void testInquireTextType_doc() {
        //given
        final String input = "http://www.snee.com/xml/xslt/sample.doc";

        //when
        final TextType actualTextType = textTypeInquirer.inquireTextType(input);

        //then
        Assert.assertTrue(actualTextType instanceof DocTextTypeImpl);
    }

    @Test
    public void testInquireTextType_pdf() {
        //given
        final String input = "http://defas.com.ua/java/Policy_of_.UA.pdf";

        //when
        final TextType actualTextType = textTypeInquirer.inquireTextType(input);

        //then
        Assert.assertTrue(actualTextType instanceof PdfTextTypeImpl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInquireTextType_emptyUrl() {
        //given
        final String input = "";

        //when
        final TextType actual = textTypeInquirer.inquireTextType(input);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInquireTextType_NullUrl() {
        //given
        final String input = null;

        final TextType actual = textTypeInquirer.inquireTextType(input);

        //then
        //expected exception
    }
}