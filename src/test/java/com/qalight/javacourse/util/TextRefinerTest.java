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
                "http://www.i.ua  https://www.i.ua  ftp://i.ua  www.i.ua";
        List<String> expected = Arrays.asList("eee", "two-two", "усіх", "one", "three", "three", "wwww",
                "http://www.i.ua", "https://www.i.ua", "ftp://i.ua", "www.i.ua");

        //when
        List<String> actual = refiner.refineText(givenText);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyParameter() {
        String text = "";
        refiner.refineText(text);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameter() {
        String text = null;
        refiner.refineText(text);
    }
}
