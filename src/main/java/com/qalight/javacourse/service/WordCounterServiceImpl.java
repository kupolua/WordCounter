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

    private final TextTypeInquirer textTypeInquirer;
    private final DocumentConverter documentConverter;
    private final WordCounter wordCounter;
    private final WordResultCollector wordResultCollector;
    private final TextRefiner refiner;

    public WordCounterServiceImpl() {
        textTypeInquirer = new TextTypeInquirer();
        documentConverter = new DocumentConverter();
        wordCounter = new WordCounter();
        wordResultCollector = new WordResultCollector();
        refiner = new TextRefiner();
    }

    @Override
    public String getWordCounterResult(String clientRequest, String sortingParam, String dataTypeResponse) {
        LOG.debug("Checking that received parameters are not null or empty.");
        checkParams(clientRequest, sortingParam);

        LOG.debug("Recognizing a type of source.");
        TextType textType = textTypeInquirer.inquireTextType(clientRequest);

        LOG.debug("Selecting an appropriate converter.");
        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

        String plainText = documentToStringConverter.convertToString(clientRequest);

        LOG.debug("Starting to refine a plain text.");
        List<String> refinedWords = refiner.getRefinedText(plainText);

        LOG.debug("Putting refined text into MAP object.");
        Map<String, Integer> countedWords = wordCounter.countWords(refinedWords);

        LOG.debug("Sorting words.");
        List<Map.Entry<String, Integer>> sortedWords = WordResultSorter.valueOf(sortingParam).getSortedWords(countedWords);

        LOG.debug("Creating JSON object.");
        String result = ResultPresentation.valueOf(dataTypeResponse).createResponse(clientRequest, sortedWords, dataTypeResponse);

        return result;
    }

    private static void checkParams(String userUrlsString, String sortingParam) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
    }

}