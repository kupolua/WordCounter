package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import info.deepidea.wordcounter.util.ErrorCodeImpl;
import info.deepidea.wordcounter.util.WordCounterRuntimeException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class DocToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(DocToStringConverter.class);

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
        Assertions.assertStringIsNotNullOrEmpty(userSourcesList);
        final Tika tika = getTika();
        URL url = createUrlObject(userSourcesList);
        String extractedText;
        try {
            extractedText = tika.parseToString(url);
        } catch (IOException e) {
            LOG.error("I/O operation has been failed or interrupted while processing <" + userSourcesList + ">.", e);
            throw new WordCounterRuntimeException(ErrorCodeImpl.CANNOT_CONNECT, userSourcesList, e);
        } catch (TikaException e) {
            LOG.error("Can't extract text from <" + userSourcesList + ">.", e);
            throw new RuntimeException("Document <" + userSourcesList + "> cannot be processed. ", e);
        }
        LOG.info("Connection to " + userSourcesList + " has been successfully established.");
        Assertions.assertStringIsNotNullOrEmpty(extractedText, userSourcesList);
        return extractedText;
    }

    protected Tika getTika() {
        return new Tika();
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
