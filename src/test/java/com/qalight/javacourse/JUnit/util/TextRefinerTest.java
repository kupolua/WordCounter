package com.qalight.javacourse.JUnit.util;

import com.qalight.javacourse.util.TextRefiner;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TextRefinerTest {

    @Test
    @Ignore
    public void testGetRefineText() {

        // given values
        String text = "Привет! Как твои дела? Отлично! Один, один, два, два, two, two!@#$%^&*()_+=!123456789";
        String expected = "привет  как твои дела  отлично  один  один  два  два  two  two                       ";
        //when
        String actual = TextRefiner.getRefineText(text);
        //then
        Assert.assertEquals(expected, actual);

    }
}