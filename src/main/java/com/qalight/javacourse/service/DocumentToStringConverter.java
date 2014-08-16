package com.qalight.javacourse.service;


public interface DocumentToStringConverter {
    public Boolean isEligible(String documentType);
    String convertToString(String userSourcesList);
}
