package info.deepidea.wordcounter.service;

public interface DocumentToStringConverter {

    public boolean isEligible(TextType documentType);

    ConvertedDataContainer convertToString(RequestContainer userRequest);
}
