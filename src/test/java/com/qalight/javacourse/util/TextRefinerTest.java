package com.qalight.javacourse.util;

import com.qalight.javacourse.util.TextRefiner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextRefinerTest {

    private TextRefiner refiner;

    @Before
    public void setup() {
        refiner = new TextRefiner();
    }

    @Test
    public void testGetRefinedText() {
        //given
        String text = "One, one ONE-oNE  Two  two, two!, three, three, усіх";

        //when
        List<String> actual = refiner.refineText(text);

        //then
        List<String> expected = Arrays.asList("one", "one", "one-one", "two", "two", "two", "three", "three", "усіх");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetRefinedTextTEST() {
        //given
        final String givenText = "[stkotok@gmail.com [One—one&#160; ONE-oNE  Two&#8two, two!, three, three—усіх]";
        System.out.println("Unrefined text: " + givenText);

        //when
        List<String> actual = refiner.refineText(givenText);
        System.out.println("  Refined text: " + actual.toString());

        //then
        String expected = "[one, one, one-one, two-two, two, three, three, усіх]";
//        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void testEmail() {
        //given
        final String givenText = "stkotok@gmail.com ";
        System.out.println("Unrefined text: " + givenText);

        //when
        List<String> actual = refiner.refineText(givenText);
        System.out.println("  Refined text: " + actual.toString());

        //then
        String expected = "[one, one, one-one, two-two, two, three, three, усіх]";
//        Assert.assertEquals(expected, actual.toString());
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
