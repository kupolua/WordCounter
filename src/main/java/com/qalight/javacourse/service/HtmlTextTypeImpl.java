package com.qalight.javacourse.service;

import com.qalight.javacourse.util.ResponseHeaderGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlTextTypeImpl implements TextType {
    private static final String[] TEXT_TYPES = {"html", "xml"};
    private static final ResponseHeaderGetter RESPONSE_HEADER_GETTER = new ResponseHeaderGetter();
    private static final Logger LOG = LoggerFactory.getLogger(HtmlTextTypeImpl.class);

    @Override
    public boolean isEligible(String dataSourceLink) {
        checkForNullOrEmpty(dataSourceLink);
        String textType = RESPONSE_HEADER_GETTER.getTextTypeByHttpHeader(dataSourceLink).toLowerCase();
        boolean isEligible = false;
        for (String type : TEXT_TYPES) {
            if (textType.contains(type)) {
                isEligible = true;
                break;
            }
        }
        return isEligible;
    }

    private void checkForNullOrEmpty(String dataSourceLink) {
        if (dataSourceLink == null) {
            LOG.error("\"dataSourceLink\" received parameter is NULL");
            throw new IllegalArgumentException(
                    "It is impossible to determine the type of the document because the link is null."
            );
        }

        if (dataSourceLink.equals("")) {
            throw new IllegalArgumentException(
                    "It is impossible to determine the type of the document because the link is empty."
            );
        }
    }
}
