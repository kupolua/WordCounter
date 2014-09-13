package com.qalight.javacourse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class TextRefinerTest {

    private TextRefiner refiner;

    @Before
    public void setup() {
        refiner = new TextRefiner();
    }

    @Test
    public void testRefineText() {
        //given
        String givenText = "eEe - two-two усіх—oNe&#160;:, thre/E!: three- -wwww,  " +
                "http://www.i.ua  https://www.i.ua  ftp://i.ua  www.i.ua " +
                "<http://www.i.ua>  <https://www.i.ua>  <ftp://i.ua>  <www.i.ua> ";
        List<String> expected = Arrays.asList("eee", "two-two", "усіх", "one", "three", "three", "wwww",
                "http://www.i.ua", "https://www.i.ua", "ftp://i.ua", "www.i.ua",
                "http://www.i.ua", "https://www.i.ua", "ftp://i.ua", "www.i.ua");

        //when
        List<String> actual = refiner.refineText(givenText);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyParameter() {
        //given
        final String TEXT = "";

        //when
        refiner.refineText(TEXT);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameter() {
        //given
        final String TEXT = null;

        //when
        refiner.refineText(TEXT);

        //then
        //expected exception
    }
}
