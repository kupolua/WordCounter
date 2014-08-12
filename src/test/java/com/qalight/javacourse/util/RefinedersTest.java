package com.qalight.javacourse.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Vova on 11.08.2014.
 */
public class RefinedersTest {

    @Ignore
    @Test
    public void testGetRefineText() {
        //todo vkamenniy: text should be in english
        // given values
        String text = "Привет! Как твои дела? Отлично! Один-один, два-два, two - two!@#$%^&*()_+=!123456789";
        String expected = "привет как твои дела отлично один-один два-два two-two";
        //when
        String actual = Refineder.getRefineText(text);
        //then
        Assert.assertEquals(expected, actual);

    }
}
