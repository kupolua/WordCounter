package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.core.WordResultSorter;
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

    private String unrefinedPlainText;
    private String refinedPlainText;
    private String textLink;
    private String documentType;
    private String result;
    private TextType textType;
    private DocumentToStringConverter documentConverter;
    private Map<String, Integer> countedWords;
    private Map<String, Integer> sortedWords;
    private DataSourceSplitter sources = new DataSourceSplitter();
    private WordCounter wordCounter = new WordCounter();
    private WordResult wordResult = new WordResult();
    private WordResultSorter wordResultSorter = new WordResultSorter();
    private ResultPresentation resultPresentation = new ResultPresentation();

    @Override
    public String getWordCounterResult(String clientRequest, String sortingParam) {
        checkParams(clientRequest, sortingParam);

        sources.split(clientRequest);

        Iterator iterator = sources.getSource().iterator();
        while (iterator.hasNext()) {
            textLink = iterator.next().toString();
            assignTextType(textLink);

            documentType = textType.get();

            documentConverter = getDocumentConverter(documentType);
            unrefinedPlainText = documentConverter.convertToString(textLink);

            refinedPlainText = Refineder.getRefineText(unrefinedPlainText);
            countedWords = wordCounter.countWords(refinedPlainText);

            //todo: Should we use ENUM or Strategy pattern in wordResultSorter?
            sortedWords = wordResultSorter.keyAscending(countedWords);

            wordResult.setWordResult(textLink, sortedWords);

        }
        result = resultPresentation.create(wordResult);

        return result;
    }

    private TextType assignTextType(String textLink) {
        Set<TextType> textTypes = new HashSet<>();
        textTypes.add(new HtmlTextTypeIml());
        textTypes.add(new XmlTextTypeImpl());

        for (TextType sourceType : textTypes) {
            if (sourceType.isEligable(textLink)) {
                textType = sourceType;
                break;
            }
        }
        return textType;
    }


    private DocumentToStringConverter getDocumentConverter(String sourceType) {
        Set<DocumentToStringConverter> documentToStringConverters = new HashSet<>();
        documentToStringConverters.add(new HtmlToStringConverter());
        documentToStringConverters.add(new XmlToStringConverter());

        for (DocumentToStringConverter documentToStringConverter : documentToStringConverters) {
            if (documentToStringConverter.isEligable(sourceType)) {
                documentConverter = documentToStringConverter;
                break;
            }
        }
        //todo: throw Exception - documentConverterNotFound
        return documentConverter;
    }

    private static void checkParams(String userUrlsString, String sortingParam) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
//        Assertions.assertStringIsNotNullOrEmpty(getTypeStatisticResult);
    }

}