package com.qalight.javacourse;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by box on 07.06.2014
 */
public class HtmlToStringConverter implements ToStringConverter{
    private static final Logger LOG = LoggerFactory.getLogger(HtmlToStringConverter.class);
    private final HtmlToPlainText htmlToPlainText;

    public HtmlToStringConverter(){
        htmlToPlainText = new HtmlToPlainText();
    }

    @Override
    public String convertToString(String userUrl){

        LOG.debug("Getting plain text.");
        Document html= null;

        try {
            html = Jsoup.connect(userUrl).get();
            LOG.info("Connection to " + userUrl + " has been successfully established.");
        } catch (IOException e) {
            LOG.error("Can't connect to " + userUrl, e);
        }

        String htmlText = String.valueOf(html);
        Document document = Jsoup.parse(htmlText);

        return htmlToPlainText.getPlainText(document);
    }
}
