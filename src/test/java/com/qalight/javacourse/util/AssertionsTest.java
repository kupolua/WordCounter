package com.qalight.javacourse.util;

import org.junit.Test;

public class AssertionsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsNull() {
        //given
        final String DATA = null;

        //when
        Assertions.assertStringIsNotNullOrEmpty(DATA);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsEmpty() {
        //given
        final String DATA = " ";

        //when
        Assertions.assertStringIsNotNullOrEmpty(DATA);

        //then
        //expected exception
    }

    @Test
    public void testAssertStringIsNotNullOrEmpty() {
        //given
        final String DATA = "src/main/webapp/index.html";

        //when
        Assertions.assertStringIsNotNullOrEmpty(DATA);

        //then
        // no exception thrown
    }

    //todo: stkotk: Add tests to method with class parameter, if Alex say Ok to this method
}