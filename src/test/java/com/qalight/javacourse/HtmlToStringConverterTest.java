package com.qalight.javacourse;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class HtmlToStringConverterTest {

    @Test
    // todo: remove 'throws Exception everywhere where not necessary'
    // todo: move to integration test. unit tests should not depend on internet connection
    public void testGetPlainTextByUrl() throws Exception {

        //   given
        final String testUrl = "http://www.mono-project.com/Java";
        InputStream in;
        in = this.getClass().getClassLoader().getResourceAsStream("expectedResultForHtmlToStringConverterTest.txt");
        final String expectedResult = IOUtils.toString(in, "UTF-8");
//        final String expectedResult = new String(
//                Files.readAllBytes(Paths.get("./src/test/resources/expectedResultForHtmlToStringConverterTest.txt")));

        // when
        ToStringConverter HTMLToTextConverter = new HtmlToStringConverter();
        String actualResult = HTMLToTextConverter.convertToString(testUrl);

        // then
        Assert.assertEquals(expectedResult, actualResult);

    }
}