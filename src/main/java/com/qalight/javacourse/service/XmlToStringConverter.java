package com.qalight.javacourse.service;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class XmlToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(XmlToStringConverter.class);
    private final HtmlToPlainText htmlToPlainText;

    public XmlToStringConverter(){
        htmlToPlainText = new HtmlToPlainText();
    }

    @Override
    public Boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if(documentType instanceof XmlTextTypeImpl){
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public String convertToString(String userUrl) {

        String fixedUrl = fixUrl(userUrl);

        LOG.debug("Getting plain text.");
        Document xml;

        try {
            xml = Jsoup.connect(fixedUrl).get();
        } catch (IOException e) {
            LOG.error("Can't connect to " + fixedUrl, e);
            throw new RuntimeException("Can't connect to: " + fixedUrl);
        }

        LOG.info("Connection to " + fixedUrl + " has been successfully established.");

        return htmlToPlainText.getPlainText(xml);
    }

    private String fixUrl(String userUrl){
        final String HTTP_PREFIX = "http://";
        final String HTTPS_PREFIX = "https://";
        String sourcesWithoutWhitespaces = userUrl.replaceAll(" ", "");
        if(!(sourcesWithoutWhitespaces.startsWith(HTTPS_PREFIX) || sourcesWithoutWhitespaces.startsWith(HTTP_PREFIX))){
            LOG.info("Try to fix URL: " + sourcesWithoutWhitespaces);
            sourcesWithoutWhitespaces = HTTP_PREFIX + sourcesWithoutWhitespaces;
        }
        return sourcesWithoutWhitespaces;
    }
}
