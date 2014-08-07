package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

public class HTMLToTextConverterTest {

    @Test
    // todo: remove 'throws Exception everywhere where not necessary'
    public void testGetPlainTextByUrl() {

        //   given
        final String testUrl = "http://english-e-books.net/books/advanced/Charlie_and_the_Chocolate_Factory-Dahl_Roald/Charlie_and_the_Chocolate_Factory-Dahl_Roald.txt";
        final int expectedTextSize = 156832;

        // when
        HTMLToTextConverter HTMLToTextConverter = new HTMLToTextConverter();
        String actualText = HTMLToTextConverter.getPlainTextByUrl(testUrl);

        // then
        Assert.assertSame(testUrl.getClass(), actualText.getClass());
        Assert.assertEquals(expectedTextSize, actualText.length());
    }
}