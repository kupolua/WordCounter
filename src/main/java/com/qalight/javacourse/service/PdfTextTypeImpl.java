package com.qalight.javacourse.service;

/**
 * Created by box on 27.08.2014.
 */
public class PdfTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = "pdf";

    @Override
    public boolean isEligible(String dataSourceLink) {
        return dataSourceLink.endsWith(TEXT_TYPE);
    }
}
