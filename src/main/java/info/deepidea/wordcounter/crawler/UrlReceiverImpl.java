package info.deepidea.wordcounter.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class UrlReceiverImpl implements UrlReceiver {
    private static final Pattern ANY_HOST_FILTER = Pattern.compile("^http(s)?://(www\\.)?[\\w\\d\\W&&[^#]]+$");
    private static final Pattern BINARY_TYPES = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
                                                + "|png|tiff?|mid|mp2|mp3|mp4"
                                                + "|wav|avi|mov|mpeg|ram|m4v|pdf"
                                                + "|rm|svg|wmv|swf|wma|zip|rar|gz))$");

    private final ParsingProcessor processor;

    @Autowired
    public UrlReceiverImpl(ParsingProcessor processor) {
        this.processor = processor;
    }

    @Override
    public Set<String> getUrlsFromPage(String url, boolean internalOnly) {
        final CrawledUrlsContainer urlsContainer = processor.parsePage(url);
        final List<String> urlsFromParsedPage = urlsContainer.getGainedUrls();
        final Set<String> result;

        if (urlsFromParsedPage.isEmpty()) {
            return Collections.emptySet();
        }

        if (internalOnly) {
            result = getInternalUrls(urlsContainer.getProcessedUrl(), urlsFromParsedPage);
        } else {
            result = getAnyUrls(urlsFromParsedPage);
        }
        return result;
    }

    private Set<String> getInternalUrls(String url, List<String> urlsFromParsedPage) {
        final String host = getHost(url);
        final Set<String> internalUrls = new HashSet<String>();
        final String pattern = "^http(s)?://(www\\.)?" + host + "[\\w\\d\\W&&[^#]]+$";

        for (String each : urlsFromParsedPage) {
            if (Pattern.matches(pattern, each)
                    && !BINARY_TYPES.matcher(each.toLowerCase()).matches()) {
                internalUrls.add(each);
            }
        }
        return internalUrls;
    }

    private Set<String> getAnyUrls(List<String> urlsFromParsedPage) {
        final Set<String> anyUrls = new HashSet<>();

        for (String each : urlsFromParsedPage) {
            if (ANY_HOST_FILTER.matcher(each).matches()
                    && !BINARY_TYPES.matcher(each.toLowerCase()).matches()) {
                anyUrls.add(each);
            }
        }
        return anyUrls;
    }

    private String getHost(String url) {
        final String host;
        try {
            host = new URL(url).getHost();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create a URL object from " + url, e); //todo: invent something
        }
        return host;
    }
}
