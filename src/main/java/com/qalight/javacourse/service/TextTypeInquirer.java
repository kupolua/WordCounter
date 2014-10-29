package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        Assertions.assertStringIsNotNullOrEmpty(clientRequest);
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(clientRequest)) {
                textType = sourceType;
                break;
            }
        }
        if(textType == null){
            throw new IllegalArgumentException("Unknown text type at " + clientRequest + ".");
        }
        LOG.debug("Document type of " + clientRequest + " identified successfully as " + textType + ".");
        return textType;
    }
}
