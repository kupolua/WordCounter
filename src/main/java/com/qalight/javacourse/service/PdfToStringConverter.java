package com.qalight.javacourse.service;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.ErrorCodeImpl;
import com.qalight.javacourse.util.WordCounterRuntimeException;
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
    public String convertToString(String userSourcesList) {
        Assertions.assertStringIsNotNullOrEmpty(userSourcesList);
        PdfReader reader = null;
        try {
            reader = getPdfReader(userSourcesList);
        } catch (IOException e) {
            String msg = "Can't connect to ";
            LOG.error(msg + userSourcesList, e);
            throw new WordCounterRuntimeException(ErrorCodeImpl.CANNOT_CONNECT, userSourcesList, e);
        } finally {
            if (reader != null) reader.close();
        }
        LOG.info("Connection to " + userSourcesList + " has been successfully established.");
        String extractedText = getTextFromAllPages(reader);
        Assertions.assertStringIsNotNullOrEmpty(extractedText, userSourcesList);
        return extractedText;
    }

    protected PdfReader getPdfReader(String userUrl) throws IOException {
        return new PdfReader(userUrl);
    }

    protected String getTextFromAllPages(PdfReader reader) {
        StringJoiner joiner = new StringJoiner(" ");
        final int numberOfPages = reader.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; ++i) {
            String text = "";
            try {
                final PdfTextExtractor pdfTextExtractor = getPdfTextExtractor(reader);
                text = pdfTextExtractor.getTextFromPage(i);
            } catch (Exception e) { // todo hard fix for StringIndexOutOfBoundsException in Pdf extract library
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
