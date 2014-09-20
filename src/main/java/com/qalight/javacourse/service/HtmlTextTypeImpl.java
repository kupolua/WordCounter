package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;

public class HtmlTextTypeImpl implements TextType {
    private static final String[] TEXT_TYPES = {"htm", "html", "xml"};

    @Override
    public boolean isEligible(String textHttpHeader) {
        Assertions.assertStringIsNotNullOrEmpty(textHttpHeader, HtmlTextTypeImpl.class);
        boolean isEligible = false;
        for (String type : TEXT_TYPES) {
            if (textHttpHeader.contains(type)) {
                isEligible = true;
                break;
            }
        }
        return isEligible;
    }
}
