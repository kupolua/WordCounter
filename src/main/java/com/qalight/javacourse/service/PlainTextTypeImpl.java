package com.qalight.javacourse.service;

import com.qalight.javacourse.util.ResponseHeaderGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by box on 08.09.2014.
 */

public class PlainTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = "plain_text_type";
    private static final ResponseHeaderGetter RESPONSE_HEADER_GETTER = new ResponseHeaderGetter();
    private static final Logger LOG = LoggerFactory.getLogger(PlainTextTypeImpl.class);

    @Override
    public boolean isEligible(String userRequest) {
        checkForNullOrEmpty(userRequest);
        String textType = RESPONSE_HEADER_GETTER.getTextTypeByHttpHeader(userRequest).toLowerCase();
        return textType.contains(TEXT_TYPE);
    }

    private void checkForNullOrEmpty(String userRequest) {
        if (userRequest == null) {
            LOG.warn("\"userRequest\" parameter is NULL.");
            throw new IllegalArgumentException("Cannot recognize a document type. The link is null.");
        }

        if (userRequest.equals("")) {
            LOG.warn("\"userRequest\" parameter is empty.");
            throw new IllegalArgumentException("Cannot recognize a document type. The link is empty.");
        }
    }
}
