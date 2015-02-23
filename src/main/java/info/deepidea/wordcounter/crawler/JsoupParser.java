package info.deepidea.wordcounter.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JsoupParser implements ParsingProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(JsoupParser.class);

    @Override
    public CrawledUrlsContainer parsePage(String url) {
        final int timeout = 3000;
        CrawledUrlsContainer container;
        Document document;
        String processedUri;
        try {
            document = Jsoup.connect(url).timeout(timeout).get();
            processedUri = document.baseUri();
        } catch (IllegalArgumentException | IOException e) {
            LOG.error("Can't connect to " + url, e);
            container = new CrawledUrlsContainer(url, Collections.emptyList());
            return container;
        }
        container = new CrawledUrlsContainer(processedUri, getAbsoluteUrls(document));
        return container;
    }

    private List<String> getAbsoluteUrls(Document document) {
        List<String> urlsOfThePage = new ArrayList<String>();
        Elements urls = document.select("a[href]");
        for (Element each : urls) {
            String absoluteUrl = each.attr("abs:href");
            urlsOfThePage.add(absoluteUrl);
        }

        return urlsOfThePage;
    }
}
