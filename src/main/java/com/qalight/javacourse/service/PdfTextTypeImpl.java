package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class PdfTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = "pdf";

    @Override
    public boolean isEligible(String textHttpHeader) {
        Assertions.assertStringIsNotNullOrEmpty(textHttpHeader, PdfTextTypeImpl.class);
        return textHttpHeader.contains(TEXT_TYPE);
    }
}
