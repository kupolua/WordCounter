package com.qalight.javacourse.service;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

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
        final String musicUrl = "music.com";
        final String userAgentValue = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36";
        final String uidCookieKey = "D_UID";
        final String hidCookieKey = "D_HID";
        final String uidCookieValue = "96DC1544-2BB7-3018-A58B-BBB6C494B28E";
        final String hidCookieValue = "gDKdGwE5yRwSXim3uD1VCiLNZqkl9oXRq8Dbrpspdjg";

        Document html;
        try {
            if (userUrl.contains(musicUrl)) {
                html = Jsoup.connect(userUrl).userAgent(userAgentValue).cookie(uidCookieKey, uidCookieValue).cookie(hidCookieKey, hidCookieValue).get();
            } else {
                html = Jsoup.connect(userUrl).userAgent(userAgentValue).get();
            }
        } catch (IOException e) {
            LOG.error("Can't connect to " + userUrl, e);
            throw new RuntimeException("Can't connect to: " + userUrl, e);
        }
        return html;
    }
}
