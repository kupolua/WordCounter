package com.qalight.javacourse.service;

public class PlainToStringConverter implements DocumentToStringConverter {
    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof PlainTextTypeImpl) {
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public String convertToString(String userRequest) {
        return userRequest;
    }
}
