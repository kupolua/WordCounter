package info.deepidea.wordcounter.crawler;

public interface ParsingProcessor {
    CrawledUrlsContainer parsePage(String url);
}
