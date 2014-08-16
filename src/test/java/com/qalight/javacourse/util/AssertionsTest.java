package com.qalight.javacourse.util;

import org.junit.Test;

public class AssertionsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsNotNull() throws Exception {
        //given
        final String DATA = null;
        //when
        Assertions.assertStringIsNotNullOrEmpty(DATA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertStringIsNotEmpty() throws Exception {
        //given
        final String DATA = " ";
        //when
        Assertions.assertStringIsNotNullOrEmpty(DATA);
    }

    //todo: add test with without throwing exception
}