package com.qalight.javacourse;

import org.junit.Test;

public class UserRequestRouterTest {

    @Test
    public void testGetCountedWords() throws Exception {

        for (UserRequestRouter userRequestRouter : UserRequestRouter.values()) {
            switch (userRequestRouter) {
                case CONSOLIDATEDRESULT:
                    break;
                case EACHURLSRESULT:
                    break;
                default:
                    throw new IllegalArgumentException(
                            "UserRequestRouter enam contain not provided value!\n" +
                                    userRequestRouter.toString());
            }
        }

        for (String s : new String[]{"CONSOLIDATEDRESULT", "EACHURLSRESULT"}) {
            UserRequestRouter.valueOf(s);
        }

    }


}