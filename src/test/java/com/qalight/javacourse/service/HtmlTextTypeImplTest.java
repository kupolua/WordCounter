package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HtmlTextTypeImplTest {
    @Spy
    private HtmlTextTypeImpl spyHtmlTextType;

    @Test
    public void testIsEligible_validUrlAndTextType() {
        // given
        final String validTypeUrl = "http://bbc.com";

        doReturn(true).when(spyHtmlTextType).isWebProtocol(anyString());

        // when
        final boolean actualResult = spyHtmlTextType.isEligible(validTypeUrl);

        // then
        verify(spyHtmlTextType, times(1)).isEligible(anyString());
        verify(spyHtmlTextType, times(1)).isWebProtocol(anyString());
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidTextTypesUrls() {
        // given
        final Set<String> expectedSet = new TreeSet(Arrays.asList(new String[]{
                "http://defas.com.ua/java/textForTest.doc",
                "http://defas.com.ua/java/textForTest.docx",
                "http://defas.com.ua/java/textForTest.odp",
                "http://defas.com.ua/java/textForTest.ods",
                "http://defas.com.ua/java/textForTest.odt",
                "http://defas.com.ua/java/textForTest.pdf",
                "http://defas.com.ua/java/textForTest.ppt",
                "http://defas.com.ua/java/textForTest.pptx",
                "http://defas.com.ua/java/textForTest.rtf",
                "http://defas.com.ua/java/textForTest.txt",
                "http://defas.com.ua/java/textForTest.xls",
                "http://defas.com.ua/java/textForTest.xlsx"
        }));

        doReturn(true).when(spyHtmlTextType).isWebProtocol(anyString());

        // when
        Set<String> actualSet = new TreeSet<>();
        for (String validTextUrl : expectedSet) {
            if (!spyHtmlTextType.isEligible(validTextUrl)) {
                actualSet.add(validTextUrl);
            }
        }

        // then
        verify(spyHtmlTextType, times(12)).isEligible(anyString());
        verify(spyHtmlTextType, times(12)).isWebProtocol(anyString());
        Assert.assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testIsEligible_invalidUrl() {
        // given
        final String dataSourceLink = "bbc.com";

        doReturn(false).when(spyHtmlTextType).isWebProtocol(anyString());

        // when
        boolean actualResult = spyHtmlTextType.isEligible(dataSourceLink);

        // then
        verify(spyHtmlTextType, times(1)).isEligible(anyString());
        verify(spyHtmlTextType, times(1)).isWebProtocol(anyString());
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String dataSourceLink = "";

        // when
        spyHtmlTextType.isEligible(dataSourceLink);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_whitespaceRequest() {
        // given
        final String dataSourceLink = " ";

        // when
        spyHtmlTextType.isEligible(dataSourceLink);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        // given
        final String dataSourceLink = null;

        // when
        spyHtmlTextType.isEligible(dataSourceLink);

        // then
        // exception thrown
    }
}