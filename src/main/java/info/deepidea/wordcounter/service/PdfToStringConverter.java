package info.deepidea.wordcounter.service;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import info.deepidea.wordcounter.util.Assertions;
import info.deepidea.wordcounter.util.ErrorCodeImpl;
import info.deepidea.wordcounter.util.WordCounterRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.StringJoiner;

@Component("pdf")
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
    public ConvertedDataContainer convertToString(RequestContainer userRequest) {
        Assertions.assertStringIsNotNullOrEmpty(userRequest.getClientRequest());
        PdfReader reader = null;
        try {
            reader = getPdfReader(userRequest.getClientRequest());
        } catch (IOException e) {
            String msg = "Can't connect to ";
            LOG.error(msg + userRequest.getClientRequest(), e);
            throw new WordCounterRuntimeException(ErrorCodeImpl.CANNOT_CONNECT, userRequest.getClientRequest(), e);
        } finally {
            if (reader != null) reader.close();
        }
        LOG.info("Connection to " + userRequest.getClientRequest() + " has been successfully established.");
        String extractedText = getTextFromAllPages(reader);
        Assertions.assertStringIsNotNullOrEmpty(extractedText, userRequest.getClientRequest());

        return new ConvertedDataContainer(userRequest.getClientRequest(), extractedText);
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
