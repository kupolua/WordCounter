package info.deepidea.wordcounter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DocumentConverter {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentConverter.class);
    private static Set<DocumentToStringConverter> documentToStringConverters;

    @Autowired
    public DocumentConverter(@Qualifier("html") DocumentToStringConverter htmlConverter,
                             @Qualifier("pdf") DocumentToStringConverter pdfConverter,
                             @Qualifier("doc") DocumentToStringConverter docConverter,
                             @Qualifier("plainText") DocumentToStringConverter plainTextConverter,
                             @Qualifier("xls") DocumentToStringConverter xlsConverter,
                             @Qualifier("xlsx") DocumentToStringConverter xlsxConverter){
        documentToStringConverters = new HashSet<>();
        documentToStringConverters.add(htmlConverter);
        documentToStringConverters.add(pdfConverter);
        documentToStringConverters.add(docConverter);
        documentToStringConverters.add(plainTextConverter);
        documentToStringConverters.add(xlsConverter);
        documentToStringConverters.add(xlsxConverter);
    }

    public DocumentToStringConverter getDocumentConverter(TextType sourceType) {
        DocumentToStringConverter documentConverter = null;
        for (DocumentToStringConverter documentToStringConverter : documentToStringConverters) {
            if (documentToStringConverter.isEligible(sourceType)) {
                documentConverter = documentToStringConverter;
                break;
            }
        }
        if (documentConverter == null) {
            LOG.warn("Cannot recognize a class (" + sourceType.getClass() + ")");
            throw new RuntimeException("Cannot recognize a class (" + sourceType.getClass() + ") in order to select appropriate converter.");
        }
        return documentConverter;
    }
}
