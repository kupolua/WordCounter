package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.util.Assertions;

public enum SupportedHttpProtocol {
    Http("http://"),
    Https("https://");

    private final String text;
    private SupportedHttpProtocol(String text) {
        this.text = text;
    }

    public static boolean isWebProtocol(String clientRequest) {
        Assertions.assertStringIsNotNullOrEmpty(clientRequest);
        boolean result = false;
        final String requestInTheLowerCase = clientRequest.toLowerCase();
        final String trimmedRequest = requestInTheLowerCase.trim();
        for (SupportedHttpProtocol protocol : SupportedHttpProtocol.values()) {
            if (trimmedRequest.startsWith(protocol.text)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
