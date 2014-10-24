package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class DocTextTypeImplTest {
    private DocTextTypeImpl docTextType;

    @Before
    public void setUp() throws Exception {
        docTextType = new DocTextTypeImpl();
    }

    @Test
    public void testIsEligible_validTextUrls() throws Exception {
        // given
        final Set<String> expectedSet = new TreeSet(Arrays.asList(new String[]{
                "http://defas.com.ua/java/textForTest.doc",
                "http://defas.com.ua/java/textForTest.docx",
                "http://defas.com.ua/java/textForTest.odp",
                "http://defas.com.ua/java/textForTest.ods",
                "http://defas.com.ua/java/textForTest.odt",
                "http://defas.com.ua/java/textForTest.ppt",
                "http://defas.com.ua/java/textForTest.pptx",
                "http://defas.com.ua/java/textForTest.rtf",
                "http://defas.com.ua/java/textForTest.txt",
                "http://defas.com.ua/java/textForTest.xls",
                "http://defas.com.ua/java/textForTest.xlsx"
        }));

        // when
        Set<String> actualSet = new TreeSet<>();
        for (String validTextUrl : expectedSet) {
            if (docTextType.isEligible(validTextUrl)) {
                actualSet.add(validTextUrl);
            }
        }

        // then
        Assert.assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testIsEligible_notValidTypeOfUrl() {
        // given
        final String dataSourceLink = "http://defas.com.ua/java/textForTest.iso";

        // when
        boolean actualResult = docTextType.isEligible(dataSourceLink);

        // then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyUrl() {
        // given
        final String dataSourceLink = " ";

        // when
        docTextType.isEligible(dataSourceLink);

        // then
        // exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullUrl() {
        //given
        final String dataSourceLink = null;

        //when
        docTextType.isEligible(dataSourceLink);

        //then
        // exception thrown
    }
}