package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class PlainTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = "plain_text_type";

    @Override
    public boolean isEligible(String textHttpHeader) {
        Assertions.assertStringIsNotNullOrEmpty(textHttpHeader, PlainTextTypeImpl.class);
        return textHttpHeader.equals(TEXT_TYPE);
    }
}
