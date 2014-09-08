package com.qalight.javacourse.service;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HtmlToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(HtmlToStringConverter.class);
    private final HtmlToPlainText htmlToPlainText;

    public HtmlToStringConverter() {
        htmlToPlainText = new HtmlToPlainText();
    }

    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof HtmlTextTypeImpl) {
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public String convertToString(String userUrl) {

        Document html;
        try {
            html = Jsoup.connect(userUrl).get();
        } catch (IOException e) {
            LOG.error("Can't connect to " + userUrl, e);
            throw new RuntimeException("Can't connect to: " + userUrl, e);
        }
        LOG.info("Connection to " + userUrl + " has been successfully established.");

        return htmlToPlainText.getPlainText(html);
    }
}
