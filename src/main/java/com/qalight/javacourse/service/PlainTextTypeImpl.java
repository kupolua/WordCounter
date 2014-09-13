package com.qalight.javacourse.service;

public class PlainTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = "plain_text_type";

    @Override
    public boolean isEligible(String dataSourceLink) {
        return dataSourceLink.endsWith(TEXT_TYPE);
    }
}
