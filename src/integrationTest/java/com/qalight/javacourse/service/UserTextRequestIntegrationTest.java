package com.qalight.javacourse.service;

import javafx.util.converter.ShortStringConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class UserTextRequestIntegrationTest {
    String sortingField = "1";
    String sortingOrder = "descending";
    String isFilterWords= "1";

    @Autowired
    WordFilter wordFilter;

    @Test
    public void testGetSortedMap() throws Exception {
        // given
        new UserTextRequest(sortingField, sortingOrder, isFilterWords);
        final Map<String, Integer> refinedWords = new TreeMap<String, Integer>(){{
            put("world", 1);
            put("the", 1);
            put("love", 1);
        }};

        // when
        Map<String,Integer> actualMap = wordFilter.removeUnimportantWords(refinedWords);

        System.out.println(actualMap);
    }
}