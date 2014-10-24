package com.qalight.javacourse.service;

import com.qalight.javacourse.testutils.AssertionsForUnitTests;
import com.qalight.javacourse.util.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

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
        assertTrue(AssertionsForUnitTests.equalCollections(expected, actual));
    }

    @Test
    public void testGetSplitRequests_plainText() throws Exception {
        // given
        final String someNonHttpText = "Common simple текст \n https://four ";

        // when
        Collection<String> actual = splitter.getSplitRequests(someNonHttpText);

        // then
        List<String> expected = Arrays.asList(someNonHttpText);
        assertTrue(AssertionsForUnitTests.equalCollections(expected, actual));
    }


}