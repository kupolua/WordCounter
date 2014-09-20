package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.ResponseHeaderGetter;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.Set;

@Component
public class TextTypeInquirer {
    private final ResponseHeaderGetter RESPONSE_HEADER_GETTER;
    private static final Logger LOG = LoggerFactory.getLogger(TextTypeInquirer.class);

    public TextTypeInquirer() {
        RESPONSE_HEADER_GETTER = new ResponseHeaderGetter();
    }

    private static Set<TextType> textTypes;
    static{
        textTypes = new HashSet<>();
        textTypes.add(new HtmlTextTypeImpl());
        textTypes.add(new PdfTextTypeImpl());
        textTypes.add(new DocTextTypeImpl());
        textTypes.add(new PlainTextTypeImpl());
    }

    public TextType inquireTextType(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink, TextTypeInquirer.class);
        String textHttpHeader = RESPONSE_HEADER_GETTER.getHttpHeader(dataSourceLink).toLowerCase();
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
