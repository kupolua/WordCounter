package info.deepidea.wordcounter.service;

public final class RequestContainer {
    private final String clientRequest;
    private final boolean crawlingRequired;
    private final boolean internalOnly;

    public RequestContainer(String clientRequest) {
        this(clientRequest, false, false);
    }
    public RequestContainer(String clientRequest, boolean crawlingRequired, boolean internalOnly) {
        this.clientRequest = clientRequest;
        this.crawlingRequired = crawlingRequired;
        this.internalOnly = internalOnly;
    }

    public String getClientRequest() {
        return clientRequest;
    }

    public boolean isCrawlingRequired() {
        return crawlingRequired;
    }

    public boolean isInternalOnly() {
        return internalOnly;
    }
}
