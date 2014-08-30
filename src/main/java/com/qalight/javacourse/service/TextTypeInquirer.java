package com.qalight.javacourse.service;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TextTypeInquirer {

    private static Set<TextType> textTypes;
    static{
        textTypes = new HashSet<>();
        textTypes.add(new HtmlTextTypeImpl());
        textTypes.add(new PdfTextTypeImpl());
    }
    public TextType inquireTextType(String textLink) {
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(textLink)) {
                textType = sourceType;
                break;
            }
        }
        if(textType == null){
            throw new IllegalArgumentException("Unknown text type.(" + textLink + ")");
        }
        return textType;
    }
}
