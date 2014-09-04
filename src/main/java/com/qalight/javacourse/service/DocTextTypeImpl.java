package com.qalight.javacourse.service;

import com.qalight.javacourse.util.ResponseHeaderGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by box on 03.09.2014.
 */
public class DocTextTypeImpl implements TextType {
    private static final String[] TEXT_TYPES = {"rtf", "opendocument", "openxmlformats-officedocument", "msword" };
    private static final ResponseHeaderGetter RESPONSE_HEADER_GETTER = new ResponseHeaderGetter();
    private static final Logger LOG = LoggerFactory.getLogger(DocTextTypeImpl.class);

    @Override
    public boolean isEligible(String dataSourceLink) {
        checkForNullOrEmpty(dataSourceLink);
        String textType = RESPONSE_HEADER_GETTER.getTextTypeByHttpHeader(dataSourceLink).toLowerCase();
        LOG.info("TextType" + textType);
        boolean isEligible = false;
        for(String type : TEXT_TYPES){
            if(textType.contains(type)){
                isEligible = true;
                break;
            }
        }
        return isEligible;
    }

    private void checkForNullOrEmpty(String dataSourceLink) {
        if (dataSourceLink == null) {
            LOG.warn("\"dataSourceLink\" parameter is NULL.");
            throw new NullPointerException("Cannot recognize a document type. The link is null.");
        }

        if (dataSourceLink.equals("")) {
            LOG.warn("\"dataSourceLink\" parameter is empty.");
            throw new IllegalArgumentException("Cannot recognize a document type. The link is empty." );
        }
    }
}
