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
    }

    public TextType inquireTextType(String textLink) {
        checkForNullOrEmpty(textLink);
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(textLink)) {
                textType = sourceType;
                break;
            }
        }
        if(textType == null){
            throw new IllegalArgumentException("Unknown text type. (" + textLink + ")");
        }
        return textType;
    }

    private void checkForNullOrEmpty(String textLink) {
        if (textLink == null) {
            LOG.error("\"textLink\" received parameter is NULL");
            throw new IllegalArgumentException(
                    "It is impossible to determine the type of the document because the link is null.");
        }
        if (textLink.trim().equals("")) {
            throw new IllegalArgumentException(
                    "It is impossible to determine the type of the document because the link is empty.");
        }
    }
}
