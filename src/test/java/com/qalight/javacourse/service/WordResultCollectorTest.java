package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class WordResultCollectorTest {

    /**
     * stkotok тут у меня к Саше вопрос.
     * Этим тестом я хотел проверить две вещи:
     * 1) не изменилось ли название метода
     * 2) примет ли метод параметры, которые должен принять.
     * Если убрать этот тест, тогда кто-то случайно изменит название метода
     * или принимаемые параметры и TeamCity не заметит этого и пустит в GitHub.
     * А с таким тестом враг не пройдёт. Правильно ли я понимаю?
     */


    @Test
    @Ignore
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