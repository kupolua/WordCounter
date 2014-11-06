package com.qalight.javacourse.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class RequestSplitterImplIntegrationTest {
    @Autowired
    RequestSplitter splitter;

    @Test
    public void testGetSplitRequests_plainText() throws Exception {
        //given
        final String userRequest = "one two two,";
        final Set<String> expected = new HashSet<>();
        expected.add("one two two,");

        //when
        Collection<String> actual = splitter.getSplitRequests(userRequest);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSplitRequests_fewLinks() throws Exception {
        //given
        final String userRequest = "http://some-site.com\n" +
                                   "http://another-site.com";
        final Set<String> expected = new HashSet<>();
        expected.add("http://some-site.com");
        expected.add("http://another-site.com");

        //when
        Collection<String> actual = splitter.getSplitRequests(userRequest);

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSplitRequests_nullUserRequest() throws Exception {
        //given
        final String userRequest = null;

        //when
        Collection<String> actual = splitter.getSplitRequests(userRequest);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSplitRequests_emptyUserRequest() throws Exception {
        //given
        final String userRequest = "";

        //when
        Collection<String> actual = splitter.getSplitRequests(userRequest);

        //then
        //expected exception
    }
}