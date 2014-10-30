package com.qalight.javacourse.service;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TextTypeInquirerTest {
    @Mock private HtmlTextTypeImpl htmlType;
    @Mock private PdfTextTypeImpl pdfType;
    @Mock private DocTextTypeImpl docType;
    @Mock private PlainTextTypeImpl plainType;
    private TextTypeInquirer inquirer;
    private Set<TextType> textTypes;

    @Before
    public void setUp() throws Exception {
        inquirer = new TextTypeInquirer();
        textTypes = new HashSet<>();
        textTypes.add(htmlType);
        textTypes.add(pdfType);
        textTypes.add(docType);
        textTypes.add(plainType);

        TextTypeInquirer.setTextTypes(textTypes);
    }

    @Test
    public void testInquireTextType_html() throws Exception {
        //given
        final String clientRequest = "http://html.com/some.html";
        when(htmlType.isEligible(clientRequest)).thenReturn(true);

        //when
        final TextType actual = inquirer.inquireTextType(clientRequest);

        //then
        verify(htmlType, times(1)).isEligible(clientRequest);
        assertTrue(actual instanceof HtmlTextTypeImpl);
    }

    @Test
    public void testInquireTextType_plainText() throws Exception {
        //given
        final String clientRequest = "some text";
        when(plainType.isEligible(clientRequest)).thenReturn(true);

        //when
        final TextType actual = inquirer.inquireTextType(clientRequest);

        //then
        verify(plainType, times(1)).isEligible(clientRequest);
        assertTrue(actual instanceof PlainTextTypeImpl);
    }

    @Test
    public void testInquireTextType_pdf() throws Exception {
        //given
        final String clientRequest = "http://pdf.com/some.pdf";
        when(pdfType.isEligible(clientRequest)).thenReturn(true);

        //when
        final TextType actual = inquirer.inquireTextType(clientRequest);

        //then
        verify(pdfType, times(1)).isEligible(clientRequest);
        assertTrue(actual instanceof PdfTextTypeImpl);
    }

    @Test
    public void testInquireTextType_doc() throws Exception {
        //given
        final String clientRequest = "http://doc.com/some.doc";
        when(docType.isEligible(clientRequest)).thenReturn(true);

        //when
        final TextType actual = inquirer.inquireTextType(clientRequest);

        //then
        verify(docType, times(1)).isEligible(clientRequest);
        assertTrue(actual instanceof DocTextTypeImpl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInquireTextType_emptyUrl() {
        //given
        final String clientRequest = "";

        //when
        final TextType actual = inquirer.inquireTextType(clientRequest);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInquireTextType_NullUrl() {
        //given
        final String input = null;

        final TextType actual = inquirer.inquireTextType(input);

        //then
        //expected exception
    }
}