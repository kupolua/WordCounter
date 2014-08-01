package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StringUrlsParserTest {

    @Test
    public void testUrlList() {

        // given values
        final String userPostStringUrls = " http://htmlbook.ru/html/input ,  http://stackoverflow.com/questions/2349633/servlets-doget-and-dopost,http://www.gradle.org/docs/current/release-notes ";
        final List<String> expectedList = new ArrayList<String>() {{
            add("http://htmlbook.ru/html/input");
            add("http://stackoverflow.com/questions/2349633/servlets-doget-and-dopost");
            add("http://www.gradle.org/docs/current/release-notes");
        }};

        //when
        List<String> actualList = new StringUrlsParser().parseUrslList(userPostStringUrls);

        //then
        Assert.assertEquals(expectedList, actualList);

    }
}