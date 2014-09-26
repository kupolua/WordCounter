package com.qalight.javacourse.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test_spring_config.xml")
public class HtmlTextTypeImplIntegrationTest {

    @Autowired
    HtmlTextTypeImpl htmlTextType;

    @Test
    public void testIsEligible_html() {
        //given
        final String textHttpHeader = "[text/html; charset=windows-1251]";

        //when
        final boolean actualResult = htmlTextType.isEligible(textHttpHeader);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_xml() {
        //given
        final String textHttpHeader = "[text/xml]";

        //when
        final boolean actualResult = htmlTextType.isEligible(textHttpHeader);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_improperType() {
        //given
        final String textHttpHeader = "[application/pdf]";
        final boolean expectedResult = false;

        //when
        final boolean actualResult = htmlTextType.isEligible(textHttpHeader);

        //then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyUrl() {
        //given
        final String textHttpHeader = "";

        //when
        htmlTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullUrl() {
        //given
        final String textHttpHeader = null;

        //when
        htmlTextType.isEligible(textHttpHeader);

        //then expected exception
    }

}