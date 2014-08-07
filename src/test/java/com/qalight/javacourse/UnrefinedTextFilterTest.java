package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;


public class UnrefinedTextFilterTest {

    @Test
    public void testRefineText() throws Exception {
        UnrefinedTextFilter textFilter = new UnrefinedTextFilter();
        //given
        final String unrefinedWord = "(Play;Station)";
        //when
        String actualResult = textFilter.refineText(unrefinedWord);
        //then
        final String expectedResult = " play station ";
        Assert.assertEquals(expectedResult, actualResult);
    }
}