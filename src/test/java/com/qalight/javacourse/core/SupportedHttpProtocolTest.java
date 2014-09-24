package com.qalight.javacourse.core;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SupportedHttpProtocolTest {

    @Test
    public void testIsWebProtocol_http() {
        // given
        String text = " http://real_http.com ";

        // when
        boolean actual = SupportedHttpProtocol.isWebProtocol(text);

        // then
        assertTrue(actual);
    }

    @Test
    public void testIsWebProtocol_nonHttp() throws Exception {
        // given
        String text = " some random text not http:// ";

        // when
        boolean actual = SupportedHttpProtocol.isWebProtocol(text);

        // then
        assertFalse(actual);
    }

}