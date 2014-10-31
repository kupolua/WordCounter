package com.qalight.javacourse.service;

import com.qalight.javacourse.core.SupportedHttpProtocol;
import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class HtmlTextTypeImpl implements TextType {
    private static final String[] TEXT_TYPES =
            {".rtf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".odt", ".ods", ".odp", ".pdf", ".txt"};

    @Override
    public boolean isEligible(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink);
        boolean isEligible = false;
        if (isWebProtocol(dataSourceLink)) {
            isEligible = true;
            for (String type : TEXT_TYPES) {
                if (dataSourceLink.endsWith(type)) {
                    isEligible = false;
                    break;
                }
            }
        }
        return isEligible;
    }

    protected boolean isWebProtocol(String dataSourceLink) {
        return SupportedHttpProtocol.isWebProtocol(dataSourceLink);
    }
}
