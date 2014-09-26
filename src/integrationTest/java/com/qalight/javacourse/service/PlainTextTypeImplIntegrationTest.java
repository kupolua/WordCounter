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
public class PlainTextTypeImplIntegrationTest {

    @Autowired
    private PlainTextTypeImpl plainTextType;

    @Test
    public void testIsEligible() {
        //given
        final String textHttpHeader = "plain_text_type";

        //when
        boolean actualResult = plainTextType.isEligible(textHttpHeader);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsNotEligible() throws Exception {
        //given
        final String textHttpHeader = "[text/html]";

        //when
        boolean actualResult = plainTextType.isEligible(textHttpHeader);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullLink() {
        //given
        final String textHttpHeader = null;

        //when
        plainTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyLink() {
        //given
        final String textHttpHeader = "";

        //when
        plainTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }
}