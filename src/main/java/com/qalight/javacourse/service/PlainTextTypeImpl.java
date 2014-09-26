package com.qalight.javacourse.service;

import com.qalight.javacourse.core.SupportedHttpProtocol;
import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class PlainTextTypeImpl implements TextType {

    @Override
    public boolean isEligible(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink);
        return !SupportedHttpProtocol.isWebProtocol(dataSourceLink);
    }
}
