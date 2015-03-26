package info.deepidea.wordcounter.service;

import org.springframework.stereotype.Component;

@Component("plainText")
public class PlainToStringConverter implements DocumentToStringConverter {
    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof PlainTextTypeImpl) {
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public ConvertedDataContainer convertToString(RequestContainer userRequest) {
        final String sourceName = "PlainText";
        return new ConvertedDataContainer(sourceName, userRequest.getClientRequest());
    }
}