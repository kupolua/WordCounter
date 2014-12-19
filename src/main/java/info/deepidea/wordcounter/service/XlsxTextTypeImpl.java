package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.core.SupportedHttpProtocol;
import info.deepidea.wordcounter.util.Assertions;
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
