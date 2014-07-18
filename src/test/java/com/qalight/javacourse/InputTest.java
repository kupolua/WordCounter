package com.qalight.javacourse;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class InputTest {

    @Test
    public void testDataIn() throws Exception {

        // given
        List<String> expectedClassObject = new ArrayList<String>();

        // when
        List<String> actualClassObject = new Input().dataIn();

        // then
        Assert.assertEquals(expectedClassObject, actualClassObject);

    }

}