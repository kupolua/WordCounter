package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import info.deepidea.wordcounter.util.ErrorCodeImpl;
import info.deepidea.wordcounter.util.WordCounterRuntimeException;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Component("xls")
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
    public ConvertedDataContainer convertToString(RequestContainer userRequest) {
        Assertions.assertStringIsNotNullOrEmpty(userRequest.getClientRequest());
        InputStream stream = openStream(userRequest.getClientRequest());
        String extractedText;
        final String xls = "Xls";
        try {
            extractedText = extractXlsWithoutSheetName(stream);
        } catch (IOException e) {
            LOG.error("I/O operation has been failed or interrupted while processing <" + userRequest.getClientRequest() + ">.", e);
            throw new RuntimeException("Document " + userRequest.getClientRequest() + " cannot be processed. ", e);
        } finally {
            closeInputStream(stream);
        }

        LOG.info("Connection to " + userRequest.getClientRequest() + " has been successfully established.");
        Assertions.assertStringIsNotNullOrEmpty(extractedText, userRequest.getClientRequest());

        return new ConvertedDataContainer(xls + userRequest.getClientRequest(), extractedText);
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
