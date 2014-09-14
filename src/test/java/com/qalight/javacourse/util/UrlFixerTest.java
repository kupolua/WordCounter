package com.qalight.javacourse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UrlFixerTest {
    private UrlFixer fixer;

    @Before
    public void setUp() throws Exception {
        fixer = new UrlFixer();
    }

    @Test
    public void testFixUrl_woHttp() throws Exception {
        //given
        final String url = "google.com";

        //when
        String actualResult = fixer.fixRequest(url);

        //then
        final String expectedResult = "http://google.com";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFixUrl_withHttp() throws Exception {
        //given
        final String url = "http://google.com";

        //when
        String actualResult = fixer.fixRequest(url);

        //then
        final String expectedResult = "http://google.com";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFixUrl_plainText() throws Exception {
        //given
        final String url = "Путин Ху*ло ла, ла, ла!!!";

        //when
        String actualResult = fixer.fixRequest(url);

        //then
        final String expectedResult = "Путин Ху*ло ла, ла, ла!!!";

        Assert.assertEquals(expectedResult, actualResult);
    }
}