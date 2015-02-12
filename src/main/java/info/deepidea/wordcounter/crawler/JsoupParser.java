package info.deepidea.wordcounter.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JsoupParser implements ParsingProcessor {

    @Override
    public List<String> parsePage(String url) {
        final int timeout = 3000;
        Document document;
        try {
            document = Jsoup.connect(url).timeout(timeout).get();
        } catch (IllegalArgumentException | IOException e) {
//            LOG.error("Can't connect to " + url, e); //todo: logging
            return Collections.emptyList();
        }

        return getAbsoluteUrls(document);
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
