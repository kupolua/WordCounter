package info.deepidea.wordcounter.crawler;

import java.util.Set;

public interface UrlReceiver {
    Set<String> getUrlsFromPage(String url, boolean internalOnly);
}
