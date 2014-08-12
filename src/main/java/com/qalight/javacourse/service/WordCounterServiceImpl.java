package com.qalight.javacourse.service;

import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.Refineder;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by kpl on 30.07.2014.
 */

public class WordCounterServiceImpl implements WordCounterService {
    //todo: Is it posible to create new obj in interface?
    //todo: Create TextTypeImpl

    private static Set<TextType> textTypes;
    static{
        textTypes = new HashSet<>();
        textTypes.add(new HtmlTextTypeIml());
        textTypes.add(new XmlTextTypeImpl());
    }

    private static Set<DocumentToStringConverter> documentToStringConverters;
    static {
        documentToStringConverters = new HashSet<>();
        documentToStringConverters.add(new HtmlToStringConverter());
        documentToStringConverters.add(new XmlToStringConverter());
    }

    private TextType textType;

    @Override
    public String getWordCounterResult(String clientRequest, String sortingParam) {
        checkParams(clientRequest, sortingParam);

        sources.split(clientRequest);

        Iterator iterator = sources.getSource().iterator();
        while (iterator.hasNext()) {

            String textLink = iterator.next().toString();

            assignTextType(textLink);

            String documentType = textType.get();

            DocumentToStringConverter documentConverter = getDocumentConverter(documentType);

            String plainText = documentConverter.convertToString(textLink);

            String refinedText = Refineder.getRefineText(plainText);
            Map<String, Integer> countedWords = wordCounter.countWords(refinedText);

            Map<String, Integer> sortedWords = wordResultSorter.keyAscending(countedWords);

            wordResult.setWordResult(textLink, sortedWords);

        }
        String result = resultPresentation.create(wordResult);

        return result;
    }

    private TextType assignTextType(String textLink) {
        for (TextType sourceType : textTypes) {
            if (sourceType.isEligable(textLink)) {
                textType = sourceType;
                break;
            }
        }
        return textType;
    }


    private DocumentToStringConverter getDocumentConverter(String sourceType) {
        DocumentToStringConverter documentConverter = null;
        for (DocumentToStringConverter documentToStringConverter : documentToStringConverters) {
            if (documentToStringConverter.isEligable(sourceType)) {
                documentConverter = documentToStringConverter;
                break;
            }
        }
        return documentConverter;
    }

    private static void checkParams(String userUrlsString, String sortingParam) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
//        Assertions.assertStringIsNotNullOrEmpty(getTypeStatisticResult);
    }

}