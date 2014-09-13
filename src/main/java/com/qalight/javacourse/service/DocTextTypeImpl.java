package com.qalight.javacourse.service;

public class DocTextTypeImpl implements TextType {
    private static final String[] TEXT_TYPES = {"rtf", "opendocument", "openxmlformats", "msword", "ms-excel", "ms-powerpoint"};

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
