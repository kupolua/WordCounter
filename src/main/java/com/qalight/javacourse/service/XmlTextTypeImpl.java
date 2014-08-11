package com.qalight.javacourse.service;

/**
 * Created by pavelkulakovsky on 11.08.14.
 */
public class XmlTextTypeImpl implements TextType {
    private static final String textType = "xml";

    @Override
    public Boolean isEligable(String dataSourceLink) {
        return dataSourceLink.endsWith(textType);
    }

    @Override
    public String get() {
        return textType;
    }
}
