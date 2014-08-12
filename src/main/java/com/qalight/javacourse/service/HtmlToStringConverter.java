package com.qalight.javacourse.service;

import org.jsoup.examples.HtmlToPlainText;

/**
 * Created by kpl on 09.08.2014.
 */
public class HtmlToStringConverter implements DocumentToStringConverter {
    private final HtmlToPlainText htmlToPlainText;
    private final String DOCUMENT_TYPE = "html";

    public HtmlToStringConverter(){
        htmlToPlainText = new HtmlToPlainText();
    }

    //todo diverfd: create JUnit test
    @Override
    public Boolean isEligable(String documentType) {
        return documentType.equalsIgnoreCase(DOCUMENT_TYPE);
    }

    //todo diverfd: create JUnit test
    @Override
    public String convertToString(String userUrl) {

        //todo: implementation:
//        Document document = Jsoup.parse(userUrl);
//        return htmlToPlainText.getPlainText(document);
        return userUrl;
    }
}
