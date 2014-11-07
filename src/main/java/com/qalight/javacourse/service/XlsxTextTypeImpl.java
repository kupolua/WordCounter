package com.qalight.javacourse.service;

import com.qalight.javacourse.core.SupportedHttpProtocol;
import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class XlsxTextTypeImpl implements TextType {
    private static final String TEXT_TYPE = ".xlsx";

    @Override
    public boolean isEligible(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink);
        return isWebProtocol(dataSourceLink) && dataSourceLink.endsWith(TEXT_TYPE);
    }

    protected boolean isWebProtocol(String dataSourceLink) {
        return SupportedHttpProtocol.isWebProtocol(dataSourceLink);
    }
}
