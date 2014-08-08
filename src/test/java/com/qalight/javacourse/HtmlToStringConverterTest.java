package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

public class HtmlToStringConverterTest {

    @Test
    // todo: remove 'throws Exception everywhere where not necessary'
    // tod: move to integration test. unit tests should not depend on internet connection
    public void testGetPlainTextByUrl() throws Exception {

        //   given
        final String testUrl = "http://english-e-books.net/books/advanced/Charlie_and_the_Chocolate_Factory-Dahl_Roald/Charlie_and_the_Chocolate_Factory-Dahl_Roald.txt";
        final int expectedTextSize = 156832;

        // when
        ToStringConverter HTMLToTextConverter = new HtmlToStringConverter();
        String actualText = HTMLToTextConverter.convertToString(testUrl);

        // then
        // todo stkotok: test actual text, not size
        Assert.assertEquals(expectedTextSize, actualText.length());
    }
}