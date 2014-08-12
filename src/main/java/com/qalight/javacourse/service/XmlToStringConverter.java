package com.qalight.javacourse.service;

/**
 * Created by kpl on 09.08.2014.
 */
public class XmlToStringConverter implements DocumentToStringConverter {

    private final String DOCUMENT_TYPE = "xml";
    //todo diverfd: create JUnit test
    @Override
    public Boolean isEligable(String documentType) {
        return documentType.equalsIgnoreCase(DOCUMENT_TYPE);
    }
    //todo diverfd: create JUnit test
    @Override
    public String convertToString(String userUrl) {

        //todo: implementation:

        return userUrl;
    }
}
