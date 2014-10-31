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
public class PlainTextTypeImplTest {
    @Spy
    private PlainTextTypeImpl spyPlainTextType;

    @Test
    public void testIsEligible_plainText() {
        // given
        String plainText = "some plain text";

        doReturn(false).when(spyPlainTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyPlainTextType.isEligible(plainText);

        // then
        verify(spyPlainTextType, times(1)).isEligible(anyString());
        verify(spyPlainTextType, times(1)).isWebProtocol(anyString());
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_validHttpUrl() {
        // given
        String httpLink = "http://defas.com.ua/java/testingPage.html";

        doReturn(true).when(spyPlainTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyPlainTextType.isEligible(httpLink);

        // then
        verify(spyPlainTextType, times(1)).isEligible(anyString());
        verify(spyPlainTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_validHttpsUrl() {
        // given
        String httpsLink = "https://mail.google.com";

        doReturn(true).when(spyPlainTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyPlainTextType.isEligible(httpsLink);

        // then
        verify(spyPlainTextType, times(1)).isEligible(anyString());
        verify(spyPlainTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String url = " ";

        // when
        spyPlainTextType.isEligible(url);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        // given
        final String url = null;

        // when
        spyPlainTextType.isEligible(url);

        // then
        // exception thrown
    }
}
