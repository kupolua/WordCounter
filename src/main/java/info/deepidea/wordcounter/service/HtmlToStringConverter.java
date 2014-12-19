package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import info.deepidea.wordcounter.util.ErrorCodeImpl;
import info.deepidea.wordcounter.util.WordCounterRuntimeException;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlToStringConverter implements DocumentToStringConverter {
    private static final Logger LOG = LoggerFactory.getLogger(HtmlToStringConverter.class);
    private static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36";

    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof HtmlTextTypeImpl) {
            isEligible = true;
        }

        return isEligible;
    }

    @Override
    public String convertToString(String userSourcesList) {
        Assertions.assertStringIsNotNullOrEmpty(userSourcesList);
        final HtmlToPlainText htmlToPlainText = getHtmlToPlainText();
        Document html;
        try {
            html = getDocument(userSourcesList);
        } catch (IllegalArgumentException | IOException e){
            LOG.error("Can't connect to " + userSourcesList, e);
            throw new WordCounterRuntimeException(ErrorCodeImpl.CANNOT_CONNECT, userSourcesList, e);
        }
        LOG.info("Connection to " + userSourcesList + " has been successfully established.");
        String extractedText = htmlToPlainText.getPlainText(html);
        Assertions.assertStringIsNotNullOrEmpty(extractedText, userSourcesList);
        return extractedText;
    }

    protected HtmlToPlainText getHtmlToPlainText() {
        return new HtmlToPlainText();
    }

    protected Document getDocument(String userUrl) throws IOException {
        return Jsoup.connect(userUrl).userAgent(USER_AGENT_VALUE).get();
    }
}
