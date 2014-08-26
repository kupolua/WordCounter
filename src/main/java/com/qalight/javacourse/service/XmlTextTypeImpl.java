package com.qalight.javacourse.service;

public class XmlTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = "xml";

    @Override
    public Boolean isEligible(String dataSourceLink) {
        return dataSourceLink.endsWith(TEXT_TYPE);
    }
}
