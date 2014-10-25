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

    public TextType inquireTextType(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink);
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(dataSourceLink)) {
                textType = sourceType;
                break;
            }
        }
        if(textType == null){
            throw new IllegalArgumentException("Unknown text type at " + dataSourceLink + ".");
        }
        LOG.debug("Document type of " + dataSourceLink + " identified successfully as " + textType + ".");
        return textType;
    }
}
