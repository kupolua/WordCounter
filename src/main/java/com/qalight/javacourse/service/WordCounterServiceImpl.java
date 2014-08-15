package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.core.WordResultSorter;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.Refineder;

import java.util.List;
import java.util.Map;

/**
 * Created by kpl on 30.07.2014.
 */

public class  WordCounterServiceImpl implements WordCounterService {
    private final DataSourceSplitterTemporary temporarySplitter;
    private final TextTypeInquirer textTypeInquirer;
    private final DocumentConverter documentConverter;
    private final WordCounter wordCounter;
    private final WordResultCollector wordResultCollector;
    private final ResultPresentation resultPresentation;

    public WordCounterServiceImpl() {
        temporarySplitter = new DataSourceSplitterTemporary();
        textTypeInquirer = new TextTypeInquirer();
        documentConverter = new DocumentConverter();
        wordCounter = new WordCounter();
        wordResultCollector = new WordResultCollector();
        resultPresentation = new ResultPresentation();
    }

    //todo kupolua: createResponse JUnit test
    @Override
    public String getWordCounterResult(String clientRequest, String sortingParam) {
        checkParams(clientRequest, sortingParam);

        String validatedSource = temporarySplitter.validateSources(clientRequest);

        TextType textType = textTypeInquirer.inquireTextType(validatedSource);
        String documentType = textType.getTextType();

        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(documentType);

        String plainText = documentToStringConverter.convertToString(validatedSource);

        String refinedText = Refineder.getRefineText(plainText);
        Map<String, Integer> countedWords = wordCounter.countWords(refinedText);

        List<Map.Entry<String, Integer>> sortedWords = WordResultSorter.valueOf(sortingParam).getSortedWords(countedWords);

        String result = resultPresentation.createResponse(validatedSource, sortedWords).toString();

        return result;
    }

    private static void checkParams(String userUrlsString, String sortingParam) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
    }

}