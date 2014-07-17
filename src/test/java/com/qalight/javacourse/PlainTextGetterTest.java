package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

public class PlainTextGetterTest {

    @Test
    public void testGetPlainTextByUrl() throws Exception {

        // given Hello Stepan
        final String testUrl = "english-e-books.net/books/advanced/Charlie_and_the_Chocolate_Factory-Dahl_Roald/Charlie_and_the_Chocolate_Factory-Dahl_Roald.txt";
        final int expectedTextSize = 156832;

        // when
        PlainTextGetter plainTextGetter = new PlainTextGetter();
        String actualText = plainTextGetter.getPlainTextByUrl(testUrl);

        // then
        Assert.assertSame(testUrl.getClass(), actualText.getClass());
        Assert.assertEquals(expectedTextSize, actualText.length());

    }
}
