package com.qalight.javacourse.service;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.StringJoiner;

/**
 * Created by box on 27.08.2014.
 */
public class PdfToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(PdfToStringConverter.class);

    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if(documentType instanceof PdfTextTypeImpl){
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public String convertToString(String userUrl) {
        PdfReader reader;

        try {
            reader = new PdfReader(userUrl);
        } catch (IOException e) {
            LOG.error("Can't connect to " + userUrl, e);
            throw new RuntimeException("Can't connect to: " + userUrl);
        }
        LOG.info("Connection to " + userUrl + " has been successfully established.");

        String text = getTextFromAllPages(reader);

        reader.close();
        return text;
    }

    private String getTextFromAllPages(PdfReader reader){
        StringJoiner joiner = new StringJoiner(" ");

        for (int i = 1; i <= reader.getNumberOfPages(); ++i) {
            String text;
            try {
                text = new PdfTextExtractor(reader).getTextFromPage(i);
            } catch (IOException e) {
                LOG.error("Can't read text from page " + i, e);
                throw new RuntimeException("Can't read text from page " + i);
            }
            joiner.add(text);
        }

        return joiner.toString();
    }
}
