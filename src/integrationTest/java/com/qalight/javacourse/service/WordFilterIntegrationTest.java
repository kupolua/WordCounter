package com.qalight.javacourse.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class WordFilterIntegrationTest {
    // todo: add other tests

    @Autowired
    private WordFilter wordFilter;

    @Test
    public void testGetWordsForFilter() {
        // given
        List<String> refinedWords = new ArrayList<>(Arrays.asList("the", "love", "ocean"));

        // when
        final List<String> actualResult = wordFilter.removeUnimportantWords(refinedWords);

        // then
        Assert.assertArrayEquals(
                Arrays.asList("love" , "ocean").toArray(), actualResult.toArray(new String[actualResult.size()]));
    }
}
