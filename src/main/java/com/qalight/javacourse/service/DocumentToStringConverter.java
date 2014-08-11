package com.qalight.javacourse.service;

/**
 * Created by kpl on 09.08.2014.
 */
public interface DocumentToStringConverter {
    public Boolean isEligable(String documentType);
    String convertToString(String userSourcesList);
}
