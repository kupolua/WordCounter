package com.qalight.javacourse.service;

/**
 * Created by box on 08.09.2014.
 */
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
