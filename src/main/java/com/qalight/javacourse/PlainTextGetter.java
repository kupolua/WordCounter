package com.qalight.javacourse;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by box on 07.06.2014.
 */
public class PlainTextGetter {
    private static final Logger LOG = LoggerFactory.getLogger(PlainTextGetter.class);
    protected String getPlainTextByUrl(String url){
//        String http = "http://";
//        url = http + url;
        LOG.debug("Getting plain text.");
        Document html= null;
        try {
            html = Jsoup.connect(url).get();
        } catch (IOException e) {
            LOG.error("Can't connect to " + url, e);
        }
        LOG.info("Connection to " + url + " has been successfully established.");

        return new HtmlToPlainText().getPlainText(Jsoup.parse(String.valueOf(html)));
    }
}
