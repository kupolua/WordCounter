package com.qalight.javacourse.service;

public interface DocumentToStringConverter {

    public boolean isEligible(TextType documentType);

    String convertToString(String userSourcesList);
}
