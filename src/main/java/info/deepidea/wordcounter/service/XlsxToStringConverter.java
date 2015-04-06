package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import info.deepidea.wordcounter.util.ErrorCodeImpl;
import info.deepidea.wordcounter.util.WordCounterRuntimeException;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Component("xlsx")
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
    public ConvertedDataContainer convertToString(RequestContainer userRequest) {
        Assertions.assertStringIsNotNullOrEmpty(userRequest.getClientRequest());
        InputStream stream = openStream(userRequest.getClientRequest());
        String extractedText;
        final String xlsx = "Xlsx";
        try {
            extractedText = extractXlsxWithoutSheetName(stream);
        } catch (IOException e) {
            LOG.error("I/O operation has been failed or interrupted while processing <" + userRequest.getClientRequest() + ">.", e);
            throw new RuntimeException("Document " + userRequest.getClientRequest() + " cannot be processed. ", e);
        } finally {
            closeInputStream(stream);
        }
        Assertions.assertStringIsNotNullOrEmpty(extractedText, userRequest.getClientRequest());


        return new ConvertedDataContainer(xlsx, extractedText);
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
