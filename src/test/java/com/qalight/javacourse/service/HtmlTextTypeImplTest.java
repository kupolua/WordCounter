package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class HtmlTextTypeImplTest {
    private HtmlTextTypeImpl htmlTextType;

    @Before
    public void setUp() throws Exception {
        htmlTextType = new HtmlTextTypeImpl();
    }

    @Test
    public void testIsEligible_validUrlAndTextType() {
        // given
        final String validTypeUrl = "http://bbc.com";

        // when
        final boolean actualResult = htmlTextType.isEligible(validTypeUrl);

        // then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_invalidTextTypesUrls() throws Exception {
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
                "http://defas.com.ua/java/textForTest.xlsx",
                "some plain text here"
        }));

        // when
        Set<String> actualSet = new TreeSet<>();
        for (String validTextUrl : expectedSet) {
            if (!htmlTextType.isEligible(validTextUrl)) {
                actualSet.add(validTextUrl);
            }
        }

        // then
        Assert.assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testIsEligible_invalidUrl() {
        // given
        final String dataSourceLink = "bbc.com";

        // when
        boolean actualResult = htmlTextType.isEligible(dataSourceLink);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyRequest() {
        // given
        final String dataSourceLink = " ";

        // when
        htmlTextType.isEligible(dataSourceLink);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullRequest() {
        //given
        final String dataSourceLink = null;

        //when
        htmlTextType.isEligible(dataSourceLink);

        //then
        // exception thrown
    }
}