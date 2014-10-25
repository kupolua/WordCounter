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
        final String givenText =
                // handling lowercase/uppercase & clean pattern
                "tHReE - two-two усіх—oNe&#160;:, thre/E!: ThrEE- -WWW, іІїЇєЄёЁґҐ " +
                        // handling urls
                        "http://www.a.ua http://b.ua http://c.ua. http://d.ua, " +
                        "http://e.ua: https://www.f.ua www.g.ua http:/h.ua ftp://i.ua " +
                        // handling emails
                        "someMail@a.ua some_Mail@b.ua so.me1Mail@c.ua 1some_Mail1@d.ua " +
                        "some.Mail@e.com.ua sOMe1Mail@f.ua sOMe1Mail@g.ua. " +
                        // handling jsoup tags
                        "<http://www.i.ua>, <http://i.ua>/ aa<https://i.ua>. <www.i.ua>: " +
                        "<www.i.ua>text <someMail@g.ua> <some.Mail@h.ua> <ftp://g.ua> " +
                        // handling whitespaces symbols;     // Problem with \xA0 symbol
                        "a&#8a b[<>]b c&nbsp;c d&#160;d e\\xA0e f&ensp;f g&#8194;g h&emsp;h " +
                        " i&#8195;i j&thinsp;j k&#8201;k l&zwnj;l m&#8204;m n—n " +
                        // handling hyphens
                        "-o -p- r- ";

        final List<String> expectedResult =
                Arrays.asList("three", "two-two", "усіх", "one", "three", "three", "www", "ііїїєєёёґґ", "httphua",
                        "somemailgua", "aahttpsiua", "somemailgua", "somemailhua", "a-a", "bb", "c", "c", "d", "d",
                        "exae", "f", "f", "g", "g", "h", "h", "i", "i", "j", "j", "k", "k", "l", "l", "m", "m", "n",
                        "n", "o", "p", "r");

        //when
        List<String> actualResult = refiner.refineText(givenText);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyParameter() {
        //given
        final String givenText = " ";

        //when
        refiner.refineText(givenText);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameter() {
        //given
        final String givenText = null;

        //when
        refiner.refineText(givenText);

        //then
        //expected exception
    }
}
