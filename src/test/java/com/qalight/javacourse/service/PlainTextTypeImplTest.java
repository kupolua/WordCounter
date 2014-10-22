package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PlainTextTypeImplTest {

    private PlainTextTypeImpl plainTextType;
    private String plainText = "some plain text";
    private String httpLink = "http://defas.com.ua/java/testingPage.html";
    private String httpsLink = "https://mail.google.com";

    @Before
    public void setUp() throws Exception {
        plainTextType = new PlainTextTypeImpl();
    }

    @Test
    public void testIsEligible_plainText() {
        //given

        //when
        boolean actualResult = plainTextType.isEligible(plainText);

        //then
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testIsEligible_httpUrl() {
        //given

        //when
        boolean actualResult = plainTextType.isEligible(httpLink);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_httpsUrl() {
        //given

        //when
        boolean actualResult = plainTextType.isEligible(httpsLink);

        //then
        Assert.assertFalse(actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_empty() {
        //given
        final String url = " ";

        //when
        boolean actualResult = plainTextType.isEligible(url);

        //then
        // exception thrown
    }
}