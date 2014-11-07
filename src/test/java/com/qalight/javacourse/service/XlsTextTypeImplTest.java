package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class XlsTextTypeImplTest {
    @Spy
    private XlsTextTypeImpl spyXlsTextType;

    @Test
    public void testIsEligible_validType() {
        // given
        final String validTypeUrl = "http://defas.com.ua/java/textForTest.xls";

        doReturn(true).when(spyXlsTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyXlsTextType.isEligible(validTypeUrl);

        // then
        verify(spyXlsTextType, times(1)).isEligible(anyString());
        verify(spyXlsTextType, times(1)).isWebProtocol(anyString());
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidType() {
        // given
        final String invalidTypeUrl = "http://defas.com.ua/java/textForTest.doc";

        doReturn(true).when(spyXlsTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyXlsTextType.isEligible(invalidTypeUrl);

        // then
        verify(spyXlsTextType, times(1)).isEligible(anyString());
        verify(spyXlsTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_invalidUrl() {
        // given
        final String invalidUrl = "defas.com.ua/java/textForTest.xls";

        doReturn(false).when(spyXlsTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyXlsTextType.isEligible(invalidUrl);

        // then
        verify(spyXlsTextType, times(1)).isEligible(anyString());
        verify(spyXlsTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String url = " ";

        // when
        spyXlsTextType.isEligible(url);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        // given
        final String url = null;

        // when
        spyXlsTextType.isEligible(url);

        // then
        // exception thrown
    }
}