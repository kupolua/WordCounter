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

    public WordCounterServiceImpl() {
        textTypeInquirer = new TextTypeInquirer();
        documentConverter = new DocumentConverter();
        wordCounter = new WordCounter();
    }

    @Override
    public String getWordCounterResult(String clientRequest, String sortingParam, String dataTypeResponse) {
        checkParams(clientRequest, sortingParam);

        TextType textType = textTypeInquirer.inquireTextType(clientRequest);

        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

        String plainText = documentToStringConverter.convertToString(clientRequest);

        TextRefiner refiner = new TextRefiner();
        refiner.refineText(plainText);

        List<String> refinedWords = refiner.getRefinedWords();

        Map<String, Integer> countedWords = wordCounter.countWords(refinedWords);

        List<Map.Entry<String, Integer>> sortedWords = WordResultSorter.valueOf(sortingParam).getSortedWords(countedWords);

        String result = ResultPresentation.valueOf(dataTypeResponse).createResponse(clientRequest, sortedWords, dataTypeResponse);

        return result;
    }

    private static void checkParams(String userUrlsString, String sortingParam) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(sortingParam);
    }

}