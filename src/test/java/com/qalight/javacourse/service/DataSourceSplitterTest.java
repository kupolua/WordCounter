package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class DataSourceSplitterTest {

    private DataSourceSplitter sourceSplitter;

    @Before
    public void setup(){
        sourceSplitter = new DataSourceSplitter();
    }

    @Test
    public void testValidateSources_validSources() {
        //given
        final String unvalidatedSources = "http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html ," +
                "commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html ," +
                "https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html , https://translate.google.com.ua";
        //when
        sourceSplitter.validateSources(unvalidatedSources);
        //then
        List<String> expectedValidSources = new ArrayList<>();
        expectedValidSources.add("http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html");
        expectedValidSources.add("http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html");

        Assert.assertEquals(expectedValidSources, sourceSplitter.getValidSources());
    }

    @Test
    public void testValidateSources_invalidSources() {
        //given
        final String unvalidatedSources = "http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html ," +
                "commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html ," +
                "https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html , https://translate.google.com.ua";
        //when
        sourceSplitter.validateSources(unvalidatedSources);
        //then
        List<String> expectedInvalidSources = new ArrayList<>();
        expectedInvalidSources.add("https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html");
        expectedInvalidSources.add("https://translate.google.com.ua");

        Assert.assertEquals(expectedInvalidSources, sourceSplitter.getInvalidSources());
    }
}