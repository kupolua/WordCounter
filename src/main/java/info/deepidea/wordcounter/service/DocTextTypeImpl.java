package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.core.SupportedHttpProtocol;
import info.deepidea.wordcounter.util.Assertions;
import org.springframework.stereotype.Component;

@Component
public class DocTextTypeImpl implements TextType {
    private static final String[] TEXT_TYPES =
            {".rtf", ".doc", ".docx", ".ppt", ".pptx", ".odt", ".ods", ".odp", ".txt"};

    @Override
    public boolean isEligible(String dataSourceLink) {
        Assertions.assertStringIsNotNullOrEmpty(dataSourceLink);
        boolean isEligible = false;
        if (isWebProtocol(dataSourceLink)) {
            for (String type : TEXT_TYPES) {
                if (dataSourceLink.endsWith(type)) {
                    isEligible = true;
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
