package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class DocTextTypeImpl implements TextType {
    private static final String[] TEXT_TYPES =
            {"rtf", "opendocument", "openxmlformats", "msword", "ms-excel", "ms-powerpoint", "officedocument"};

    @Override
    public boolean isEligible(String textHttpHeader) {
        Assertions.assertStringIsNotNullOrEmpty(textHttpHeader, DocTextTypeImpl.class);
        boolean isEligible = false;
        for (String type : TEXT_TYPES) {
            if (textHttpHeader.contains(type)) {
                isEligible = true;
                break;
            }
        }
        return isEligible;
    }
}
