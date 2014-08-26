package com.qalight.javacourse.service;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HtmlToStringConverter implements DocumentToStringConverter {
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";
    private static final Logger LOG = LoggerFactory.getLogger(HtmlToStringConverter.class);
    private final HtmlToPlainText htmlToPlainText;

    public HtmlToStringConverter(){
        htmlToPlainText = new HtmlToPlainText();
    }

    @Override
    public Boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if(documentType instanceof HtmlTextTypeImpl){
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public String convertToString(String userUrl) {
        String fixedUrl = fixUrl(userUrl);

        Document html;
        try {
            html = Jsoup.connect(fixedUrl).get();
        } catch (IOException e) {
            LOG.error("Can't connect to " + fixedUrl, e);
            throw new RuntimeException("Can't connect to: " + fixedUrl);
        }
        LOG.info("Connection to " + fixedUrl + " has been successfully established.");

        return htmlToPlainText.getPlainText(html);
    }

    private String fixUrl(String userUrl){
        String sourcesWithoutWhitespaces = userUrl.replaceAll(" ", "");
        if(!(sourcesWithoutWhitespaces.startsWith(HTTPS_PREFIX) || sourcesWithoutWhitespaces.startsWith(HTTP_PREFIX))){
            LOG.info("Try to fix URL: " + sourcesWithoutWhitespaces);
            sourcesWithoutWhitespaces = HTTP_PREFIX + sourcesWithoutWhitespaces;
        }
        return sourcesWithoutWhitespaces;
    }
}
