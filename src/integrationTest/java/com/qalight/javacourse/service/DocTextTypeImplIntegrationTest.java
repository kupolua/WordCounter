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
public class DocTextTypeImplIntegrationTest {

    @Autowired
    private DocTextTypeImpl docTextType;

    @Test
    public void testIsEligible_invalidPdfType() {
        //given
        final String textHttpHeader = "[application/pdf]";
        //when
        boolean actualResult = docTextType.isEligible(textHttpHeader);
        //then
        Assert.assertFalse(actualResult);
    }

    @Test
    public void testIsEligible_validTypes() {
        //given
        final String[] textHttpHeaders = {
                "[application/msword]",
                "[application/vnd.openxmlformats-officedocument.presentationml.presentation]"
        };

        //when
        boolean actualResult = checkIfSourceIsEligible(textHttpHeaders);

        //then
        final boolean expectedResult = true;
        Assert.assertEquals(expectedResult, actualResult);
    }

    private boolean checkIfSourceIsEligible(String[] list) {
        boolean result = true;
        for (String link : list) {
            if (!docTextType.isEligible(link)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_nullLink() {
        //given
        final String textHttpHeader = null;

        //when
        docTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEligible_emptyLink() {
        //given
        final String textHttpHeader = "";

        //when
        docTextType.isEligible(textHttpHeader);

        //then
        //expected exception
    }
}