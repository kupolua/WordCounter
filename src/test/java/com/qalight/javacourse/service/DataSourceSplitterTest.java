package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class DataSourceSplitterTest {

    //todo diverfd: separate to some tests. Each public method have its test
    @Test
    public void testValidateSources() {
        DataSourceSplitter sourceSplitter = new DataSourceSplitter();
        //given
        final String notValidateSources = "http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html ," +
                "commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html ," +
                "https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html , https://translate.google.com.ua";
        //when
        sourceSplitter.validateSources(notValidateSources);
        //then
        List<String> expectedValidSources = new ArrayList<>();
        expectedValidSources.add("http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html");
        expectedValidSources.add("http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html");

        List<String> expectedInvalidSources = new ArrayList<>();
        expectedInvalidSources.add("https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/UrlValidator.html");
        expectedInvalidSources.add("https://translate.google.com.ua");

        Assert.assertEquals(expectedInvalidSources, sourceSplitter.getInvalidSources());
        Assert.assertEquals(expectedValidSources, sourceSplitter.getValidSources());
    }
}