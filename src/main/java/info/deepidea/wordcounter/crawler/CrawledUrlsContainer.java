package info.deepidea.wordcounter.crawler;

import java.util.List;

public class CrawledUrlsContainer {
    private final String processedUrl;
    private final List<String> gainedUrls;

    public CrawledUrlsContainer(String processedUrl, List<String> gainedUrls) {
        this.processedUrl = processedUrl;
        this.gainedUrls = gainedUrls;
    }

    public String getProcessedUrl() {
        return processedUrl;
    }

    public List<String> getGainedUrls() {
        return gainedUrls;
    }
}
