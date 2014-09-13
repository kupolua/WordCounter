package com.qalight.javacourse.service;

public class PdfTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = "pdf";

    @Override
    public boolean isEligible(String dataSourceLink) {
        return dataSourceLink.endsWith(TEXT_TYPE);
    }
}
