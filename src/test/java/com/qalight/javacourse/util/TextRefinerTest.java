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
    public void testRefineText_handlingUppercaseAndCleanPattern() {
        // given
        final String givenText = "tHReE - two-two усіх—oNe&#160;:, thre/E!: ThrEE- -WWW, іІїЇєЄёЁґҐ ";

        final List<String> expectedResult =
                Arrays.asList("three", "two-two", "усіх", "one", "three", "three", "www", "ііїїєєёёґґ");

        // when
        final List<String> actualResult = refiner.refineText(givenText);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRefineText_wordWrappingProcessing() {
        // given
        final String givenText = "эки-\nпаж tHReE - two-two усіх—oNe&#160;:, thre/E!: ThrEE- -WWW, іІїЇєЄёЁґҐ ";

        final List<String> expectedResult =
                Arrays.asList("экипаж", "three", "two-two", "усіх", "one", "three", "three", "www", "ііїїєєёёґґ");


        // when
        final List<String> actualResult = refiner.refineText(givenText);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRefineText_handlingUrls() {
        // given
        final String givenText = "http://www.a.ua http://b.ua http://c.ua. http://d.ua, " +
                "http://e.ua: https://www.f.ua www.g.ua http:/h.ua ftp://i.ua";

        final List<String> expectedResult = Arrays.asList("httphua");

        // when
        final List<String> actualResult = refiner.refineText(givenText);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRefineText_handlingEmails() {
        // given
        final String givenText = "someMail@a.ua some_Mail@b.ua so.me1Mail@c.ua 1some_Mail1@d.ua " +
                "some.Mail@e.com.ua sOMe1Mail@f.ua sOMe1Mail@g.ua. sOMe1Mail@h.ua, sOMe1Mail@i.ua:";

        final List<String> expectedResult =
                Arrays.asList("somemailgua", "somemailhua", "somemailiua");

        // when
        final List<String> actualResult = refiner.refineText(givenText);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRefineText_handlingJsoupTags() {
        // given
        final String givenText = "<http://www.i.ua>, <http://i.ua>/ aa<https://i.ua>. <www.i.ua>: " +
                "<www.i.ua>text <someMail@g.ua> <some.Mail@h.ua> <ftp://g.ua>";

        final List<String> expectedResult = Arrays.asList("aahttpsiua", "somemailgua", "somemailhua");

        // when
        final List<String> actualResult = refiner.refineText(givenText);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRefineText_handlingWhitespacesSymbols() {
        // given
        final String givenText = "a&#8a b[<>]b c&nbsp;c d&#160;d e\\xA0e f&ensp;f g&#8194;g h&emsp;h " +
                " i&#8195;i j&thinsp;j k&#8201;k l&zwnj;l m&#8204;m n—n";

        final List<String> expectedResult =
                Arrays.asList("a-a", "bb", "c", "c", "d", "d", "exae", "f", "f", "g", "g",
                        "h", "h", "i", "i", "j", "j", "k", "k", "l", "l", "m", "m", "n", "n");

        // when
        final List<String> actualResult = refiner.refineText(givenText);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testRefineText_handlingHyphensAndApostrophes() {
        // given
        final String givenText = "-aaa -bbb- ccc- ddd-ddd 'eee fff'fff fff'f ggg'";

        final List<String> expectedResult = Arrays.asList("aaa", "bbb", "ccc", "ddd-ddd", "eee", "fff'fff", "fff'f", "ggg'");

        // when
        final List<String> actualResult = refiner.refineText(givenText);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testEmptyParameter() {
        // given
        final String givenText = " ";

        // when
        refiner.refineText(givenText);

        // then
        // expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameter() {
        // given
        final String givenText = null;

        // when
        refiner.refineText(givenText);

        // then
        // expected exception
    }
}