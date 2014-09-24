package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.ResponseHeaderGetter;
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
    // todo 1: use spring injection instead of manual object creation
    // todo 2: do not use separate connection to check request header
    private final ResponseHeaderGetter responseHeaderGetter;

    public TextTypeInquirer() {
        responseHeaderGetter = new ResponseHeaderGetter();
    }

    public TextType inquireTextType(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink, TextTypeInquirer.class);
        String textHttpHeader = responseHeaderGetter.getHttpHeader(dataSourceLink).toLowerCase();
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(textHttpHeader)) {
                textType = sourceType;
                break;
            }
        }
        if(textType == null){
            throw new IllegalArgumentException("Unknown text type at " + dataSourceLink + ": " + textHttpHeader + ".");
        }
        LOG.debug("Document type of " + dataSourceLink + " identified successfully as " + textType + ".");
        return textType;
    }
}
