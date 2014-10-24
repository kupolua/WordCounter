package com.qalight.javacourse.util;

import org.junit.Test;

public class AssertionsTest {

    @Test
    public void testAssertStringIsNotNullOrEmpty_ok() throws Exception {
        //given
        final String string = "some text in string";

        //when
        Assertions.assertStringIsNotNullOrEmpty(string);

        //then
        // no exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsNotNullOrEmpty_null() throws Exception {
        //given
        final String nullString = null;

        //when
        Assertions.assertStringIsNotNullOrEmpty(nullString);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsNotNullOrEmpty_empty() throws Exception {
        //given
        final String emptyString = "";

        //when
        Assertions.assertStringIsNotNullOrEmpty(emptyString);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsNotNullOrEmpty_whitespace() throws Exception {
        //given
        final String emptyStringWithWhitespace = " ";

        //when
        Assertions.assertStringIsNotNullOrEmpty(emptyStringWithWhitespace);

        //then
        //expected exception
    }

    @Test
    public void testAssertObjectIsNotNull_ObgIsNotNull() throws Exception {
        //given
        final Object object = "some text";

        //when
        Assertions.assertObjectIsNotNull(object);

        //then
        // no exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertObjectIsNotNull_ObjIsNull() throws Exception {
        //given
        final Object object = null;

        //when
        Assertions.assertObjectIsNotNull(object);

        //then
        //expected exception
    }

}