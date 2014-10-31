package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PdfTextTypeImplTest {
    @Spy
    private PdfTextTypeImpl spyPdfTextType;

    @Test
    public void testIsEligible_validType() {
        // given
        final String validTypeUrl = "http://defas.com.ua/java/textForTest.pdf";

        doReturn(true).when(spyPdfTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyPdfTextType.isEligible(validTypeUrl);

        // then
        verify(spyPdfTextType, times(1)).isEligible(anyString());
        verify(spyPdfTextType, times(1)).isWebProtocol(anyString());
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidType() {
        // given
        final String invalidTypeUrl = "http://defas.com.ua/java/textForTest.doc";

        doReturn(true).when(spyPdfTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyPdfTextType.isEligible(invalidTypeUrl);

        // then
        verify(spyPdfTextType, times(1)).isEligible(anyString());
        verify(spyPdfTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_invalidUrl() {
        // given
        final String invalidUrl = "defas.com.ua/java/textForTest.pdf";

        doReturn(false).when(spyPdfTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyPdfTextType.isEligible(invalidUrl);

        // then
        verify(spyPdfTextType, times(1)).isEligible(anyString());
        verify(spyPdfTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String url = " ";

        // when
        spyPdfTextType.isEligible(url);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        // given
        final String url = null;

        // when
        spyPdfTextType.isEligible(url);

        // then
        // exception thrown
    }
}