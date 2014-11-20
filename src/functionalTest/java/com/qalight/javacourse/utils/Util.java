package com.qalight.javacourse.utils;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import static com.qalight.javacourse.utils.Constants.COUNT_URL;
import static com.qalight.javacourse.utils.Constants.PARAM_TEXT_COUNT;

public class Util {

    public static Request buildRequestWithParamValue(String requestedValue) {
        RequestBody formBody = new FormEncodingBuilder()
                .add(PARAM_TEXT_COUNT, requestedValue)
                .build();
        final Request request = new Request.Builder()
                .url(COUNT_URL)
                .post(formBody)
                .build();
        return request;
    }

    public static String createFailMessage(String countUrl, String requestedValue) {
        return "cannot get response from " + countUrl + " with request: " + requestedValue;
    }
}
