package com.qalight.javacourse.service;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RequestSplitterTest {
    private RequestSplitter splitter;

    @Before
    public void setup() {
        splitter = new RequestSplitterImpl();
    }

    @Test
    public void testGetSplitRequests_web() throws Exception {
        // given
        final String someNonHttpText = "http://one http://two   http://three \n https://four ";

        // when
        Collection<String> actual = splitter.getSplitRequests(someNonHttpText);

        // then
        List<String> expected = Arrays.asList("http://one", "http://two", "http://three", "https://four");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSplitRequests_plainText() throws Exception {
        // given
        final String someNonHttpText = "Common simple текст \n https://four ";

        // when
        Collection<String> actual = splitter.getSplitRequests(someNonHttpText);

        // then
        List<String> expected = Arrays.asList(someNonHttpText);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSplitRequests_mix() throws Exception {
        // given
        final String someNonHttpText = "http://one \n one two two three three three  https://four ";

        // when
        Collection<String> actual = splitter.getSplitRequests(someNonHttpText);

        // then
        List<String> expected =
                Arrays.asList("http://one", "one", "two", "two", "three", "three", "three", "https://four");
        assertEquals(expected, actual);
    }
}