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
