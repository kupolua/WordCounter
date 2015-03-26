package info.deepidea.wordcounter.service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ConvertedDataContainer {
    private final String sourceName;
    private final String plainText;
    private Map<String, Set<String>> relatedLinks;
    private Set<String> rawUrls;

    public ConvertedDataContainer(String sourceName, String plainText) {
        this.sourceName = sourceName;
        this.plainText = plainText;
        relatedLinks = Collections.emptyMap();
        rawUrls = Collections.emptySet();
    }

    public void setRawUrls(Set<String> rawUrls) {
        this.rawUrls = rawUrls;
    }

    public void setRelatedLinks(Map<String, Set<String>> relatedLinks) {
        this.relatedLinks = relatedLinks;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getPlainText() {
        return plainText;
    }

    public Map<String, Set<String>> getRelatedLinks() {
        return relatedLinks;
    }

    public Set<String> getRawUrls() {
        return rawUrls;
    }
}
