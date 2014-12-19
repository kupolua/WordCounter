package info.deepidea.wordcounter.service;

import info.deepidea.wordcounter.util.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TextTypeInquirer {
    private static final Logger LOG = LoggerFactory.getLogger(TextTypeInquirer.class);
    private static Set<TextType> textTypes;
    public TextTypeInquirer() {
        textTypes = new HashSet<>();
        textTypes.add(new HtmlTextTypeImpl());
        textTypes.add(new PdfTextTypeImpl());
        textTypes.add(new DocTextTypeImpl());
        textTypes.add(new PlainTextTypeImpl());
        textTypes.add(new XlsTextTypeImpl());
        textTypes.add(new XlsxTextTypeImpl());
    }

    public static void setTextTypes(Set<TextType> textTypes) {
        TextTypeInquirer.textTypes = textTypes;
    }

    public TextType inquireTextType(String clientRequest) {
        Assertions.assertStringIsNotNullOrEmpty(clientRequest);
        TextType textType = null;
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligible(clientRequest)) {
                textType = sourceType;
                break;
            }
        }
        LOG.debug("Document type of " + clientRequest + " identified successfully as " + textType + ".");
        return textType;
    }
}
