package com.qalight.javacourse.service;

public class HtmlTextTypeImpl implements TextType {
    private static final String[] TEXT_TYPES = {"htm", "html"};

    @Override
    public boolean isEligible(String dataSourceLink) {
        boolean isEligible = true;
        for (String type : TEXT_TYPES) {
            if (dataSourceLink.endsWith(type)) {
                isEligible = false;
                break;
            }
        }
        return isEligible;
    }
}
