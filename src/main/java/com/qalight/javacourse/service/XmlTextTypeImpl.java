package com.qalight.javacourse.service;

/**
 * Created by pavelkulakovsky on 11.08.14.
 */
public class XmlTextTypeImpl implements TextType {
    private static final String textType = "xml";

    //todo diverfd: create JUnit test
    @Override
    public Boolean isEligible(String dataSourceLink) {
        return dataSourceLink.endsWith(textType);
    }

    //todo diverfd: create JUnit test
    @Override
    public String getTextType() {
        return textType;
    }
}
