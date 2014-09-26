package com.qalight.javacourse.service;

import com.qalight.javacourse.core.SupportedHttpProtocol;
import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class PdfTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = ".pdf";

    @Override
    public boolean isEligible(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink);
        return SupportedHttpProtocol.isWebProtocol(dataSourceLink) && dataSourceLink.endsWith(TEXT_TYPE);
    }
}
