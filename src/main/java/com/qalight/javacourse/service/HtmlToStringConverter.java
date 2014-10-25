package com.qalight.javacourse.service;

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
    private static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36";

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
        final HtmlToPlainText htmlToPlainText = getHtmlToPlainText();
        Document html;
        try {
            html = getDocument(userUrl);
        } catch (IOException e) {
            LOG.error("Can't connect to " + userUrl, e);
            throw new RuntimeException("Can't connect to: " + userUrl, e);
        }
        LOG.info("Connection to " + userUrl + " has been successfully established.");

        return htmlToPlainText.getPlainText(html);
    }

    protected HtmlToPlainText getHtmlToPlainText() {
        return new HtmlToPlainText();
    }

    protected Document getDocument(String userUrl) throws IOException {
        return Jsoup.connect(userUrl).userAgent(USER_AGENT_VALUE).get();
    }
}
