package com.qalight.javacourse.service;

import com.qalight.javacourse.util.ResponseHeaderGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by box on 08.09.2014.
 */

public class PlainTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = "plain";
    private static final ResponseHeaderGetter RESPONSE_HEADER_GETTER = new ResponseHeaderGetter();
    private static final Logger LOG = LoggerFactory.getLogger(PlainTextTypeImpl.class);

    @Override
    public boolean isEligible(String dataSourceLink) {
        checkForNullOrEmpty(dataSourceLink);
        String textType = RESPONSE_HEADER_GETTER.getTextTypeByHttpHeader(dataSourceLink).toLowerCase();
        return textType.contains(TEXT_TYPE);
    }

    private void checkForNullOrEmpty(String dataSourceLink) {
        if (dataSourceLink == null) {
            LOG.warn("\"dataSourceLink\" parameter is NULL.");
            throw new IllegalArgumentException("Cannot recognize a document type. The link is null.");
        }

        if (dataSourceLink.equals("")) {
            LOG.warn("\"dataSourceLink\" parameter is empty.");
            throw new IllegalArgumentException("Cannot recognize a document type. The link is empty.");
        }
    }
}
