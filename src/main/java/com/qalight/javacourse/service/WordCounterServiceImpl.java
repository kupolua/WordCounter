package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.core.WordResultSorter;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.TextRefiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class WordCounterServiceImpl implements WordCounterService {
    private static final Logger LOG = LoggerFactory.getLogger(WordCounterServiceImpl.class);

    private final SingleDataSourceValidator validator;
    private final TextTypeInquirer textTypeInquirer;
    private final DocumentConverter documentConverter;
    private final WordCounter wordCounter;
    private final WordResultCollector wordResultCollector;
    private final ResultPresentation resultPresentation;
    public static String errorMessageToUser = null;
    private static String validatedSource = null;

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

        try {
            checkParams(clientRequest, sortingParam);
        } catch (IllegalArgumentException e) {
            LOG.error("IllegalArgumentException" + e);
            resultPresentation.createErrorResponse("Your request is empty.");
            String result = errorMessageToUser;
            return result;
        }

        try {
            validatedSource = validator.validateSources(clientRequest);
        } catch (IllegalArgumentException e) {
            LOG.error("IllegalArgumentException" + e);
            resultPresentation.createErrorResponse("We can't read https.");
            String result = errorMessageToUser;
            return result;
        }

        LOG.debug("Recognizing a type of source.");
        TextType textType = textTypeInquirer.inquireTextType(validatedSource);

        LOG.debug("Selecting an appropriate converter.");
        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

        String plainText = documentToStringConverter.convertToString(validatedSource);

        LOG.debug("Starting to refine a plain text.");
        String refinedText = TextRefiner.getRefineText(plainText);

        LOG.debug("Putting refined text into MAP object.");
        Map<String, Integer> countedWords = wordCounter.countWords(refinedText);

        LOG.debug("Sorting words.");
        List<Map.Entry<String, Integer>> sortedWords = WordResultSorter.valueOf(sortingParam).getSortedWords(countedWords);

        LOG.debug("Creating JSON object.");
        String result = resultPresentation.createResponse(validatedSource, sortedWords).toString();

        return result;
    }

    private static void checkParams(String userUrlsString, String sortingParam) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
    }

}