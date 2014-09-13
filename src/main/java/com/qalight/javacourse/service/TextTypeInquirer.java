package com.qalight.javacourse.service;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.Set;

@Component
public class TextTypeInquirer {
    private static final Logger LOG = LoggerFactory.getLogger(TextTypeInquirer.class);

    private static Set<TextType> textTypes;
    static{
        textTypes = new HashSet<>();
        textTypes.add(new HtmlTextTypeImpl());
        textTypes.add(new PdfTextTypeImpl());
        textTypes.add(new DocTextTypeImpl());
        textTypes.add(new PlainTextTypeImpl());
    }

    public TextType inquireTextType(String clientRequest) {
        checkForNullOrEmpty(clientRequest);
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(clientRequest)) {
                textType = sourceType;
                break;
            }
        }
        if(textType == null){
            throw new IllegalArgumentException("Unknown text type. (" + clientRequest + ")");
        }
        return textType;
    }

    private void checkForNullOrEmpty(String clientRequest) {
        if (clientRequest == null) {
            LOG.error("\"clientRequest\" received parameter is NULL");
            throw new IllegalArgumentException(
                    "It is impossible to determine the type of the document because the link is null.");
        }
        if (clientRequest.trim().equals("")) {
            throw new IllegalArgumentException(
                    "It is impossible to determine the type of the document because the link is empty.");
        }
    }
}
