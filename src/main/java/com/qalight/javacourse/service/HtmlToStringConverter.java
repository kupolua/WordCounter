package com.qalight.javacourse.service;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by kpl on 09.08.2014.
 */
public class HtmlToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(HtmlToStringConverter.class);
    private final HtmlToPlainText htmlToPlainText;

    public HtmlToStringConverter(){
        htmlToPlainText = new HtmlToPlainText();
    }

    @Override
    public Boolean isEligible(String documentType) {
        final String DOCUMENT_TYPE = "html";
        return documentType.equalsIgnoreCase(DOCUMENT_TYPE);
    }

    @Override
    public String convertToString(String userUrl) {
        LOG.debug("Getting plain text.");

        Document html;

        try {
            html = Jsoup.connect(userUrl).get();
        } catch (IOException e) {
            LOG.error("Can't connect to " + userUrl, e);
            throw new RuntimeException("Can't connect to: " + userUrl);
        }

        LOG.info("Connection to " + userUrl + " has been successfully established.");

        String htmlText = String.valueOf(html);
        Document document = Jsoup.parse(htmlText);

        return htmlToPlainText.getPlainText(document);
    }
}
