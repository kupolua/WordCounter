package com.qalight.javacourse.service;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
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
        } catch (HttpStatusException e) {
            LOG.error("Can't connect to <" + userUrl + ">. Trying with additional options.", e);
            html = convertToStringWithOptions(userUrl);
        } catch (IOException e) {
            LOG.error("Can't connect to " + userUrl, e);
            throw new RuntimeException("Can't connect to: " + userUrl, e);
        }
        LOG.info("Connection to " + userUrl + " has been successfully established.");

        return htmlToPlainText.getPlainText(html);
    }

    private Document convertToStringWithOptions(String userUrl){
        Document html;
        try {
            if (userUrl.contains("music.com")) {
                html = Jsoup.connect(userUrl).userAgent("Chrome").cookie("D_UID", "24799FB2-C9DE-3AEE-ABDC-4743294DDF81").cookie("D_HID", "jtmc7OFTq50VP90CTlESts3p+VeWWjJMZkfMIIjZ+k4").get();
            } else {
                html = Jsoup.connect(userUrl).userAgent("Chrome").get();
            }
        } catch (IOException e) {
            LOG.error("Can't connect to " + userUrl, e);
            throw new RuntimeException("Can't connect to: " + userUrl, e);
        }
        return html;
    }
}
