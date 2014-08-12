package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class WordResultCollectorTest {

    @Test
    public void testSetWordResult() {

        // given
        final String someLink = "http://some.link.com";

        final Map<String, Integer> map = new TreeMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};

        //when
        Object exeption = null;
        try {
            WordResultCollector collector = new WordResultCollector();
            collector.setWordResult(someLink, map);
        } catch (Exception e) {
            exeption = e;
        }

        //then
        Assert.assertEquals(exeption == null, true);
    }
}