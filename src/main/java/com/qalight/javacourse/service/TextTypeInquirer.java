package com.qalight.javacourse.service;

import com.qalight.javacourse.util.ResponseHeaderGetter;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.Set;

@Component
public class TextTypeInquirer {
    private static final ResponseHeaderGetter RESPONSE_HEADER_GETTER = new ResponseHeaderGetter();
    private static final Logger LOG = LoggerFactory.getLogger(TextTypeInquirer.class);

    private static Set<TextType> textTypes;
    static{
        textTypes = new HashSet<>();
        textTypes.add(new HtmlTextTypeImpl());
        textTypes.add(new PdfTextTypeImpl());
        textTypes.add(new DocTextTypeImpl());
        textTypes.add(new PlainTextTypeImpl());
    }

    public TextType inquireTextType(String dataSourceLink) {
        checkForNullOrEmpty(dataSourceLink);
        String textTypeHeader = RESPONSE_HEADER_GETTER.getTextTypeByHttpHeader(dataSourceLink).toLowerCase();
        System.out.println("textTypeHeader = " + textTypeHeader);
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(textTypeHeader)) {
                textType = sourceType;
                break;
            }
        }
        if(textType == null){
            throw new IllegalArgumentException("Unknown text type. (" + dataSourceLink + ")");
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
