package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class XlsxTextTypeImplIntegrationTest {
    @Autowired
    XlsxTextTypeImpl xlsxTextType;

    @Test
    public void testIsEligible_okType() {
        // given
        final String url = "https://dl.dropboxusercontent.com/u/12495182/About%20us.xlsx";
        final boolean expectedResult = true;

        //when
        final boolean actualResult = xlsxTextType.isEligible(url);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIsEligible_improperType() {
        //given
        final String url = "https://dl.dropboxusercontent.com/u/12495182/About%20us.doc";
        final boolean expectedResult = false;

        //when
        final boolean actualResult = xlsxTextType.isEligible(url);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_empty() {
        //given
        final String url = "";

        //when
        xlsxTextType.isEligible(url);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_null() {
        //given
        final String textHttpHeader = null;

        // when
        xlsxTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }
}