package com.qalight.javacourse.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NamingThreadFactoryTest {
    private NamingThreadFactory threadFactory;

    @Before
    public void setUp() throws Exception {
        final String threadPrefix = "word_counter_pool_thread";
        threadFactory = new NamingThreadFactory(threadPrefix);
    }

    @Test
    public void testNewThread() throws Exception {
        //given
        Runnable r = new Thread();
        final String expected = "word_counter_pool_thread-1-1-thread";

        //when
        Thread actual = threadFactory.newThread(r);

        //then
        assertEquals(expected, actual.getName());
    }
}