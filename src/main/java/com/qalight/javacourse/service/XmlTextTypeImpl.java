package com.qalight.javacourse.service;

public class XmlTextTypeImpl implements TextType {
    private static final String textType = "xml";

    @Override
    public Boolean isEligible(String dataSourceLink) {
        return dataSourceLink.endsWith(textType);
    }

    @Override
    public String getTextType() {
        return textType;
    }
}
