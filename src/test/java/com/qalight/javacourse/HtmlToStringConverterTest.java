package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlToStringConverterTest {

    @Test
    // todo: remove 'throws Exception everywhere where not necessary'
    // tod: move to integration test. unit tests should not depend on internet connection
    public void testGetPlainTextByUrl() throws Exception {

        //   given
        final String testUrl = "http://www.mono-project.com/Java";
        final String expectedResult = new String(
                Files.readAllBytes(Paths.get("src\\test\\resources\\expectedResultForHtmlToStringConverterTest.txt")));

        // when
        ToStringConverter HTMLToTextConverter = new HtmlToStringConverter();
        String actualResult = HTMLToTextConverter.convertToString(testUrl);

        // then
        Assert.assertEquals(expectedResult, actualResult);

    }
}