package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.TextRefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WordCounterServiceImpl implements WordCounterService {
    @Autowired private TextTypeInquirer textTypeInquirer;
    @Autowired private DocumentConverter documentConverter;
    @Autowired private WordCounter wordCounter;
    @Autowired private ResultPresentationService resultPresentationService;
    @Autowired private TextRefiner refiner;
    @Autowired private WordFilter wordFilter;

    @Override
    public String getWordCounterResult(String clientRequest, String dataTypeResponse) {
        checkParams(clientRequest, dataTypeResponse);

        TextType textType = textTypeInquirer.inquireTextType(clientRequest);

        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

        String plainText = documentToStringConverter.convertToString(clientRequest);

        List<String> refinedWords = refiner.refineText(plainText);

        Map<String, Integer> unFilteredWords = wordCounter.countWords(refinedWords);

        List<String> refinedWordsWithFilter = wordFilter.removeUnimportantWords(refinedWords);

        Map<String, Integer> countedWords = wordCounter.countWords(refinedWordsWithFilter);

        ResultPresentation resultPresentation = resultPresentationService.getResultPresentation(dataTypeResponse);

        String result = resultPresentation.createResponse(clientRequest, countedWords, unFilteredWords, dataTypeResponse);

        return result;
    }

    private static void checkParams(String userUrlsString, String dataTypeResponse) {
        Assertions.assertStringIsNotNullOrEmpty(userUrlsString);
        Assertions.assertStringIsNotNullOrEmpty(dataTypeResponse);
    }

}