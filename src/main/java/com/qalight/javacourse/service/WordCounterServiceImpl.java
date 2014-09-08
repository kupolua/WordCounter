package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.util.Assertions;
import com.qalight.javacourse.util.TextRefiner;
import com.qalight.javacourse.util.UrlFixer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WordCounterServiceImpl implements WordCounterService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TextTypeInquirer textTypeInquirer;
    @Autowired
    private DocumentConverter documentConverter;
    @Autowired
    private WordCounter wordCounter;
    @Autowired
    private ResultPresentationService resultPresentationService;
    @Autowired
    private UrlFixer urlFixer;

    @Override
    public String getWordCounterResult(String clientRequest, String dataTypeResponse) {
        checkParams(clientRequest, dataTypeResponse);

        String fixedUrl = urlFixer.fixRequest(clientRequest);

        TextType textType = textTypeInquirer.inquireTextType(fixedUrl);

        DocumentToStringConverter documentToStringConverter = documentConverter.getDocumentConverter(textType);

        String plainText = documentToStringConverter.convertToString(fixedUrl);

        TextRefiner refiner = new TextRefiner();

        List<String> refinedWords = refiner.refineText(plainText);

        WordFilter wordFilter = new WordFilter();
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