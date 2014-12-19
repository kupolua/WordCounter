package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.core.SupportedHttpProtocol;
import info.deepidea.wordcounter.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class PlainTextTypeImpl implements TextType {

    @Override
    public boolean isEligible(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink);
        return !isWebProtocol(dataSourceLink);
    }

    protected boolean isWebProtocol(String dataSourceLink) {
        return SupportedHttpProtocol.isWebProtocol(dataSourceLink);
    }
}
