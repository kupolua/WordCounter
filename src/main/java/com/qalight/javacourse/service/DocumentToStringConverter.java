package com.qalight.javacourse.service;


public interface DocumentToStringConverter {
    public Boolean isEligible(TextType documentType);
    String convertToString(String userSourcesList);
}
