package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.TextRefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("wordCounterService")
public class WordCounterServiceImpl implements WordCounterService {
    private final TextTypeInquirer textTypeInquirer;
    private final DocumentConverter documentConverter;
    private final WordCounter wordCounter;
    private final ResultPresentationService resultPresentationService;
    private final TextRefiner refiner;
    private final WordFilter wordFilter;

    @Autowired
    public WordCounterServiceImpl(TextTypeInquirer textTypeInquirer, DocumentConverter documentConverter,
                                  WordCounter wordCounter, ResultPresentationService resultPresentationService,
                                  TextRefiner refiner, WordFilter wordFilter) {
        this.textTypeInquirer = textTypeInquirer;
        this.documentConverter = documentConverter;
        this.wordCounter = wordCounter;
        this.resultPresentationService = resultPresentationService;
        this.refiner = refiner;
        this.wordFilter = wordFilter;
    }

    @Override
    public String getWordCounterResult(String clientRequest, String dataTypeResponse) {
        checkParams(clientRequest, dataTypeResponse);

        TextType textType = textTypeInquirer.inquireTextType(clientRequest);

        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

        String plainText = documentToStringConverter.convertToString(clientRequest);

        List<String> refinedWords = refiner.refineText(plainText);

        List<String> refinedWordsWithFilter = wordFilter.removeUnimportantWords(refinedWords);

        Map<String, Integer> countedWords = wordCounter.countWords(refinedWordsWithFilter);

        ResultPresentation resultPresentation = resultPresentationService.getResultPresentation(dataTypeResponse);

        String result = resultPresentation.createResponse(clientRequest, countedWords, dataTypeResponse);

        return result;
    }

    private static void checkParams(String userUrlsString, String dataTypeResponse) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(dataTypeResponse);
    }

}