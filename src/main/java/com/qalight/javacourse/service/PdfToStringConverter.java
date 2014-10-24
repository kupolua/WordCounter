package com.qalight.javacourse.service;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.StringJoiner;

@Component
public class PdfToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(PdfToStringConverter.class);

    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof PdfTextTypeImpl) {
            isEligible = true;
        }
        return isEligible;
    }

    @Override
    public String convertToString(String userUrl) {
        PdfReader reader = null;

        try {
            reader = getPdfReader(userUrl);
        } catch (IOException e) {
            String msg = "Can't connect to ";
            LOG.error(msg + userUrl, e);
            throw new RuntimeException(msg + userUrl, e);
        } finally {
            if (reader != null) reader.close();
        }
        LOG.info("Connection to " + userUrl + " has been successfully established.");

        return getTextFromAllPages(reader);
    }

    protected PdfReader getPdfReader(String userUrl) throws IOException {
        return new PdfReader(userUrl);
    }

    protected String getTextFromAllPages(PdfReader reader) {
        StringJoiner joiner = new StringJoiner(" ");

        for (int i = 1; i <= reader.getNumberOfPages(); ++i) {
            String text = "";
            try {
                final PdfTextExtractor pdfTextExtractor = getPdfTextExtractor(reader);
                text = pdfTextExtractor.getTextFromPage(i);
            } catch (Exception e) { // hard fix for StringIndexOutOfBoundsException in Pdf extract library
                String msg = "Can't read text from page ";
                LOG.warn(msg + i, e);
            }
            joiner.add(text);
        }

        return getString(joiner);
    }

    protected String getString(StringJoiner joiner) {
        return String.valueOf(joiner);
    }

    protected PdfTextExtractor getPdfTextExtractor(PdfReader reader) {
        return new PdfTextExtractor(reader);
    }
}
