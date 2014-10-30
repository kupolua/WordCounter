package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;

public class PdfTextTypeImplTest {
    @Spy
    @Mock
    PdfTextTypeImpl mockPdfTextType;
//    @Spy PdfTextTypeImpl spyPdfTextType;

    @Before
    public void setUp() throws Exception {
        mockPdfTextType = new PdfTextTypeImpl();
    }

    @Test
    public void testIsEligible_validType() {
        // given
        final String validTypeUrl = "http:// defas.com.ua/java/textForTest.pdf";
        final boolean isWebProtocol = true;

        doReturn(isWebProtocol).when(mockPdfTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = mockPdfTextType.isEligible(validTypeUrl);

        // then
        verify(mockPdfTextType, times(1)).isWebProtocol(anyString());
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidType() {
        // given
        final String invalidTypeUrl = "http:// defas.com.ua/java/textForTest.doc";

        // when
        boolean actualResult = mockPdfTextType.isEligible(invalidTypeUrl);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_invalidUrl() {
        // given
        final String invalidUrl = "defas.com.ua/java/textForTest.pdf";

        // when
        boolean actualResult = mockPdfTextType.isEligible(invalidUrl);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String url = " ";

        // when
        mockPdfTextType.isEligible(url);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        // given
        final String url = null;

        // when
        mockPdfTextType.isEligible(url);

        // then
        // exception thrown
    }
}