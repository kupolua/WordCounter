package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DataSourceSplitterTemporaryTest {
    private DataSourceSplitterTemporary temporarySplitter;

    @Before
    public void setup(){
        temporarySplitter = new DataSourceSplitterTemporary();
    }

    @Test
    public void testValidateSources_withoutWWW() throws Exception {
        //given
        final String SOURCE = "http://bbc.com";
        //when
        String actualResult = temporarySplitter.validateSources(SOURCE);
        //then
        String expectedResult = "http://bbc.com";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testValidateSources_withoutHttpAndWWW() throws Exception {
        //given
        final String SOURCE = "bbc.com";
        //when
        String actualResult = temporarySplitter.validateSources(SOURCE);
        //then
        String expectedResult = "http://bbc.com";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testValidateSources_withoutHttp() throws Exception {
        //given
        final String SOURCE = "www.bbc.com";
        //when
        String actualResult = temporarySplitter.validateSources(SOURCE);
        //then
        String expectedResult = "http://www.bbc.com";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateSources_withHttps() throws Exception {
        //given
        final String SOURCE = "https://bbc.com";
        //when
        temporarySplitter.validateSources(SOURCE);
    }
}