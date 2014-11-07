package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class XlsxToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(XlsxToStringConverter.class);

    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof XlsxTextTypeImpl) {
            isEligible = true;
        }

        return isEligible;
    }

    @Override
    public String convertToString(String userSourcesList) {
        Assertions.assertStringIsNotNullOrEmpty(userSourcesList);
        InputStream stream = openStream(userSourcesList);
        String extractedText;
        try {
            extractedText = extractXlsxWithoutSheetName(stream);
        } catch (IOException e) {
            LOG.error("I/O operation has been failed or interrupted while processing <" + userSourcesList + ">.", e);
            throw new RuntimeException("Document " + userSourcesList + " cannot be processed. ", e);
        } finally {
            closeInputStream(stream);
        }

        return extractedText;
    }

    protected String extractXlsxWithoutSheetName(InputStream input) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(input);
        XSSFExcelExtractor extractor = new XSSFExcelExtractor(wb);
        extractor.setIncludeSheetNames(false);
        String text = extractor.getText();
        return text;
    }

    protected InputStream openStream(String userSourcesList) {
        URL url = createUrlObject(userSourcesList);
        InputStream stream;
        try {
            stream = url.openStream();
        } catch (IOException e) {
            LOG.error("Cannot open a stream " + userSourcesList, e);
            throw new RuntimeException("Cannot open a stream" + userSourcesList, e);
        }

        return stream;
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

    private void closeInputStream(InputStream stream) {
        if(stream != null){
            try {
                stream.close();
            } catch (IOException e) {
                LOG.error("Cannot close InputStream", e);
                throw new RuntimeException("Cannot close InputStream", e);
            }
        }
    }
}
