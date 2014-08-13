package com.qalight.javacourse.service;

/**
 * Created by kpl on 09.08.2014.
 */
public interface DocumentToStringConverter {
    public Boolean isEligible(String documentType);
    String convertToString(String userSourcesList);
}
