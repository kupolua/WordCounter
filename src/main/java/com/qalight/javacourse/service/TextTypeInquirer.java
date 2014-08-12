package com.qalight.javacourse.service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kpl on 12.08.2014.
 */
public class TextTypeInquirer {

    private static Set<TextType> textTypes;
    static{
        textTypes = new HashSet<>();
        textTypes.add(new HtmlTextTypeImpl());
        textTypes.add(new XmlTextTypeImpl());
    }
    //todo diverfd: create tests
    public TextType inquireTextType(String textLink) {
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(textLink)) {
                textType = sourceType;
                break;
            }
        }
        return textType;
    }
}
