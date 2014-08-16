package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.core.WordResultSorter;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.Refineder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by kpl on 30.07.2014.
 */

public class WordCounterServiceImpl implements WordCounterService {
    private static final Logger LOG = LoggerFactory.getLogger(WordCounterServiceImpl.class);

    private final SingleDataSourceValidator validator;
    private final TextTypeInquirer textTypeInquirer;
    private final DocumentConverter documentConverter;
    private final WordCounter wordCounter;
    private final WordResultCollector wordResultCollector;
    private final ResultPresentation resultPresentation;
    public static String errorMessageToUser = null;

    public WordCounterServiceImpl() {
        validator = new SingleDataSourceValidator();
        textTypeInquirer = new TextTypeInquirer();
        documentConverter = new DocumentConverter();
        wordCounter = new WordCounter();
        wordResultCollector = new WordResultCollector();
        resultPresentation = new ResultPresentation();
    }

    @Override
    public String getWordCounterResult(String clientRequest, String sortingParam) {
        LOG.debug("Checking that received parameters are not null or empty.");
        checkParams(clientRequest, sortingParam);

        String validatedSource = validator.validateSources(clientRequest);

        LOG.debug("Recognizing a type of source.");
        TextType textType = textTypeInquirer.inquireTextType(validatedSource);

        LOG.debug("Getting a type of source.");
        String documentType = textType.getTextType();

        LOG.debug("Selecting an appropriate converter.");
        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(documentType);

        String plainText = documentToStringConverter.convertToString(validatedSource);

        LOG.debug("Starting to refine a plain text.");
        String refinedText = Refineder.getRefineText(plainText);

        LOG.debug("Putting refined text into MAP object.");
        Map<String, Integer> countedWords = wordCounter.countWords(refinedText);

        LOG.debug("Sorting words.");
        List<Map.Entry<String, Integer>> sortedWords = WordResultSorter.valueOf(sortingParam).getSortedWords(countedWords);

        LOG.debug("Creating JSON object.");
        String result = resultPresentation.createResponse(validatedSource, sortedWords).toString();

        if (errorMessageToUser != null) {
            String errorMessageToUserFromCode = errorMessageToUser;
            result = errorMessageToUserFromCode;
        }

        return result;
    }

    private static void checkParams(String userUrlsString, String sortingParam) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
    }

}