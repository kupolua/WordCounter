package info.deepidea.wordcounter.service;

public interface DocumentToStringConverter {

    public boolean isEligible(TextType documentType);

    String convertToString(String userSourcesList);
}
