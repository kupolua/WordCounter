package info.deepidea.wordcounter.core;

import info.deepidea.wordcounter.util.Assertions;

public enum SupportedHttpProtocol {
    Http("http://"),
    Https("https://");

    private final String text;
    private SupportedHttpProtocol(String text) {
        this.text = text;
    }

    public static boolean isWebProtocol(String str) {
        Assertions.assertStringIsNotNullOrEmpty(str);
        boolean result = false;
        for (SupportedHttpProtocol protocol : SupportedHttpProtocol.values()) {
            if (str.trim().startsWith(protocol.text)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
