package com.qalight.javacourse.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by box on 03.09.2014.
 */
public class DocToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(DocToStringConverter.class);
    private final Tika tika;

    public DocToStringConverter() {
        tika = new Tika();
    }

    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof DocTextTypeImpl) {
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public String convertToString(String userSourcesList) {
        URL url = createUrlObject(userSourcesList);
        String text;
        try {
            text = tika.parseToString(url);
        } catch (IOException e) {
            LOG.error("I/O operation has been failed or interrupted while processing <" + userSourcesList + ">.", e);
            throw new RuntimeException("Document <" + userSourcesList + "> cannot be processed. ", e);
        } catch (TikaException e) {
            LOG.error("Can't extract text from <" + userSourcesList + ">.", e);
            throw new RuntimeException("Document <" + userSourcesList + "> cannot be processed. ", e);
        }

        return text;
    }

    private URL createUrlObject(String userSourcesList) {
        URL url;
        try {
            url = new URL(userSourcesList);
        } catch (MalformedURLException e) {
            LOG.error("Cannot create URL object <" + userSourcesList + ">. ", e);
            throw new RuntimeException("<" + userSourcesList + "> cannot be processed.", e);
        }

        return url;
    }
}
