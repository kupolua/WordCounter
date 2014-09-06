package com.qalight.javacourse.util;

import com.qalight.javacourse.util.UrlFixer;
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
        String actualResult = fixer.fixUrl(url);

        //then
        final String expectedResult = "http://google.com";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFixUrl_withHttp() throws Exception {
        //given
        final String url = "http://google.com";

        //when
        String actualResult = fixer.fixUrl(url);

        //then
        final String expectedResult = "http://google.com";

        Assert.assertEquals(expectedResult, actualResult);
    }
}