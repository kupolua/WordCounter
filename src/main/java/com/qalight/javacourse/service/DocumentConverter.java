package com.qalight.javacourse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DocumentConverter {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentConverter.class);
    private static Set<DocumentToStringConverter> documentToStringConverters;
    public DocumentConverter(){
        documentToStringConverters = new HashSet<>();
        documentToStringConverters.add(new HtmlToStringConverter());
        documentToStringConverters.add(new PdfToStringConverter());
        documentToStringConverters.add(new DocToStringConverter());
        documentToStringConverters.add(new PlainToStringConverter());
        documentToStringConverters.add(new XlsToStringConverter());
        documentToStringConverters.add(new XlsxToStringConverter());
    }

    public static void setDocumentToStringConverters(Set<DocumentToStringConverter> documentToStringConverters) {
        DocumentConverter.documentToStringConverters = documentToStringConverters;
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
