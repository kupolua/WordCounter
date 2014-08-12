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
        textTypes.add(new HtmlTextTypeIml());
        textTypes.add(new XmlTextTypeImpl());
    }

    public TextType inquireTextType(String textLink) {
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligable(textLink)) {
                textType = sourceType;
                break;
            }
        }
        return textType;
    }
}
