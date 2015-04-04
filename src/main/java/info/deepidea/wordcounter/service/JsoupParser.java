package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import info.deepidea.wordcounter.util.ErrorCodeImpl;
import info.deepidea.wordcounter.util.WordCounterRuntimeException;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class JsoupParser implements ParsingProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(JsoupParser.class);

    @Override
    public ConvertedDataContainer parsePage(RequestContainer request) {
        final int timeout = 3000;
        Assertions.assertStringIsNotNullOrEmpty(request.getClientRequest());

        Document document;
        String processedUri;
        try {
            document = Jsoup.connect(request.getClientRequest()).timeout(timeout).get();
            processedUri = document.baseUri();
        } catch (IllegalArgumentException | IOException e) {
            LOG.error("Can't connect to " + request.getClientRequest(), e);
            throw new WordCounterRuntimeException(ErrorCodeImpl.CANNOT_CONNECT, request.getClientRequest(), e);
        }
        LOG.info("Connection to " + request.getClientRequest() + " has been successfully established.");

        final String extractedText = getPlainText(document);
        Assertions.assertStringIsNotNullOrEmpty(extractedText, request.getClientRequest());
        final ConvertedDataContainer container = new ConvertedDataContainer(processedUri, extractedText);

        if (request.isCrawlingRequired()) {
            Set<String> rawUrls = getAbsoluteUrls(document);
            container.setRawUrls(rawUrls);
        }

        return container;
    }

    private String getPlainText(Document document) {
        final HtmlToPlainText htmlToPlainText = new HtmlToPlainText();
        final String plainText = htmlToPlainText.getPlainText(document);
        return plainText;
    }

    private Set<String> getAbsoluteUrls(Document document) {
        Set<String> urlsOfThePage = new HashSet<>();
        Elements urls = document.select("a[href]");
        for (Element each : urls) {
            String absoluteUrl = each.attr("abs:href");
            urlsOfThePage.add(absoluteUrl);
        }

        return urlsOfThePage;
    }
}
