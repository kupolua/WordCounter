package com.qalight.javacourse.jUnit;

import com.qalight.javacourse.HtmlToStringConverter;
import com.qalight.javacourse.ToStringConverter;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class HtmlToStringConverterTest {

    @Test
    // todo stkotok: remove 'throws Exception everywhere where not necessary'
    // tod: move to integration test. unit tests should not depend on internet connection
    public void testGetPlainTextByUrl() throws Exception {

        //   given
        final String testUrl = "http://www.mono-project.com/Java";
        InputStream in;
        in = this.getClass().getClassLoader().getResourceAsStream("expectedResultForHtmlToStringConverterTest.txt");
        final String expectedResult = IOUtils.toString(in, "UTF-8");

        // when
        ToStringConverter HTMLToTextConverter = new HtmlToStringConverter();
        String actualResult = HTMLToTextConverter.convertToString(testUrl);

        // then
        Assert.assertEquals(expectedResult, actualResult);

    }
}