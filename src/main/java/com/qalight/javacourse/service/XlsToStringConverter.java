package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.ErrorCodeImpl;
import com.qalight.javacourse.util.WordCounterRuntimeException;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class XlsToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(XlsToStringConverter.class);

    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof XlsTextTypeImpl) {
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
            extractedText = extractXlsWithoutSheetName(stream);
        } catch (IOException e) {
            LOG.error("I/O operation has been failed or interrupted while processing <" + userSourcesList + ">.", e);
            throw new RuntimeException("Document " + userSourcesList + " cannot be processed. ", e);
        } finally {
            closeInputStream(stream);
        }
        Assertions.assertStringIsNotNullOrEmpty(extractedText, userSourcesList);
        return extractedText;
    }

    protected String extractXlsWithoutSheetName(InputStream input) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook (input);
        ExcelExtractor extractor = new ExcelExtractor(wb);
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
            throw new WordCounterRuntimeException(ErrorCodeImpl.CANNOT_CONNECT, userSourcesList, e);
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
